package com.mfk.ws.java8new;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Co {


   static class Dog {

       @NotNull
        private int leg;
        private int age;
        public Dog() {

        }

        public int getLeg() {
            return leg;
        }

        public void setLeg(int leg) {
            this.leg = leg;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

       @Override
       public String toString() {
           return "Dog{" +
                   "leg=" + leg +
                   ", age=" + age +
                   '}';
       }
   }

    public static void main(String[] args) {
        Dog dog1= new Dog();
        dog1.setAge(2);
        dog1.setLeg(4);
        Dog dog2= new Dog();
        dog2.setAge(2);
        dog2.setLeg(3);
        Dog dog3= new Dog();
        dog3.setAge(6);
        dog3.setLeg(4);
        List dogs = new ArrayList();
        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);


        String[] strings =  {"","ab","d"};
        List<String> list = Arrays.asList(strings);
        System.out.println(list);
        String[] arr = (String[])list.toArray();
        System.out.println(arr.toString());
        System.out.println(arr[1]);
        System.out.println(list.stream().count());
        System.out.println("================");
        Stream<String> stream = list.stream().filter((s) -> {
            System.out.println(s);
            return !s.isEmpty();
        }).distinct();
        stream.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("forEach:"+s);
            }
        });
       // System.out.println(stream.collect(Collectors.toList()));
        dogs.stream().forEach(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });
        Object newdogs = dogs.stream().map(s -> {
            return ((Dog)s).getAge();
        }).collect(Collectors.toList());

        System.out.println(newdogs);

        System.out.println(dogs);
        Object newdogs1 = dogs.stream().filter(s -> {
            return ((Dog)s).getAge()>1;
        }).sorted(( Object s1,  Object s2) -> {
            System.out.println("====sorted====");
            System.out.println(s1);
            return  ((Dog)s2).getAge()- ((Dog)s1).getAge();
        }).collect(Collectors.toMap(s -> {
                 return ((Dog)s).getAge()+  new Random().nextInt();},
                  s -> {
                    return ((Dog)s).getLeg();})
            );

        System.out.println(newdogs1);

        Object newdogs2 = dogs.stream().filter(s -> {
            return ((Dog)s).getAge()>1;
        }).sorted(( Object s1,  Object s2) -> {
            System.out.println("====sorted====");
            System.out.println(s1);
            return  ((Dog)s2).getAge()- ((Dog)s1).getAge();
        }).collect(Collectors.groupingBy(s -> {
            return ((Dog)s).getAge();
                })
        );

        System.out.println(newdogs2);


        Object newdogs3 = dogs.stream().filter(s -> {
            return ((Dog)s).getAge()>1;
        }).collect(Collectors.toList()
        );

        System.out.println(newdogs3);




    }

}
