package com.mfk.ws.handler;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class WsHandler extends TextWebSocketHandler implements WebSocketHandler {


        private Logger logger = LoggerFactory.getLogger(WsHandler.class);
        // 在线用户列表
        private static final Map<String, WebSocketSession> users;
        // 用户标识
        private static final String CLIENT_ID = "mchNo";

        static {
            users = new HashMap<>();
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            logger.info("成功建立websocket-spring连接");
            String mchNo = getMchNo(session);
            if (StringUtils.isNotEmpty(mchNo)) {
                users.put(mchNo, session);
                session.sendMessage(new TextMessage("成功建立websocket-spring连接"));
                logger.info("用户标识：{}，Session：{}", mchNo, session.toString());
            }
        }

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) {
            logger.info("WS收到客户端消息999999999999999：{}", message.getPayload());
            JSONObject msgJson = JSONObject.parseObject(message.getPayload());
            String to = msgJson.getString("to");
            String msg = msgJson.getString("msg");
            WebSocketMessage<?> webSocketMessageServer = new TextMessage("server:" +message);
            try {
                //Thread.sleep(10000);
                //boolean open = session.isOpen();
                session.sendMessage(webSocketMessageServer);
                session.close();
                if("all".equals(to.toLowerCase())) {
                    sendMessageToAllUsers(new TextMessage(getMchNo(session) + ":" +msg));
                }else {
                    logger.info("一个人");
                    sendMessageToUser(to, new TextMessage(getMchNo(session) + ":" +msg));
                }
            } catch (Exception e) {
                logger.info("handleTextMessage method error：{}", e);
            }
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            if (session.isOpen()) {
                session.close();
            }
            logger.info("连接出错");
            users.remove(getMchNo(session));
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            logger.info("连接已关闭：" + status);
            // users.remove(getMchNo(session));
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }

        public void sendMessage(String jsonData) throws Exception {
            logger.info("收到客户端消息sendMessage：{}", jsonData);
            JSONObject msgJson = JSONObject.parseObject(jsonData);
            String mchNo = StringUtils.isEmpty(msgJson.getString(CLIENT_ID)) ? "陌生人" : msgJson.getString(CLIENT_ID);
            String to = msgJson.getString("to");
            String msg = msgJson.getString("msg");

            if("all".equals(to.toLowerCase())) {
                sendMessageToAllUsers(new TextMessage(mchNo + ":" +msg));
            }else {
                sendMessageToUser(to, new TextMessage(mchNo + ":" +msg));
            }
        }

        /**
         * 发送信息给指定用户
         * @Title: sendMessageToUser
         * @Description: TODO
         * @Date 2018年8月21日 上午11:01:08
         * @author OnlyMate
         * @param mchNo
         * @param message
         * @return
         */
        public boolean sendMessageToUser(String mchNo, TextMessage message) {
            if (users.get(mchNo) == null) {
                logger.info("sendMessageToUser mchNo:", mchNo);
                return false;
            }

            WebSocketSession session = users.get(mchNo);
            logger.info("sendMessage：{} ,msg：{}", session, message.getPayload());
            if (!session.isOpen()) {
                logger.info("客户端:{},已断开连接，发送消息失败", mchNo);
                return false;
            }
            try {
                session.sendMessage(message);
                session.close();
            } catch (IOException e) {
                logger.info("sendMessageToUser method error：{}", e);
                return false;
            }
            return true;
        }

        /**
         * 广播信息
         * @Title: sendMessageToAllUsers
         * @Description: TODO
         * @Date 2018年8月21日 上午11:01:14
         * @author OnlyMate
         * @param message
         * @return
         */
        public boolean sendMessageToAllUsers(TextMessage message) {
            boolean allSendSuccess = true;
            Set<String> mchNos = users.keySet();
            WebSocketSession session = null;
            for (String mchNo : mchNos) {
                try {
                    session = users.get(mchNo);
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }else {
                        logger.info("客户端:{},已断开连接，发送消息失败", mchNo);
                    }
                } catch (IOException e) {
                    logger.info("sendMessageToAllUsers method error：{}", e);
                    allSendSuccess = false;
                }
            }

            return allSendSuccess;
        }

        /**
         * 获取用户标识
         * @Title: getMchNo
         * @Description: TODO
         * @Date 2018年8月21日 上午11:01:01
         * @author OnlyMate
         * @param session
         * @return
         */
        private String getMchNo(WebSocketSession session) {
            try {
                String mchNo = session.getAttributes().get(CLIENT_ID).toString();
                System.out.println("mchNo===={}"+mchNo);
                return mchNo;
            } catch (Exception e) {
                return null;
            }
        }
}
