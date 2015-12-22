package com.lijeeshk.paso;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lijeesh on 20/12/15.
 */
public class Test {

    public static void main(String[] args) {

        Observable<String> obs1 = Observable.create(subscriber -> {
            subscriber.onNext("text ");
        });
        Observable<String> obs2 = Observable.create(subscriber -> {
            //subscriber.onNext(" for ");
            subscriber.onError(new RuntimeException("second error"));
        });

        Observable<String> res = obs1.zipWith(obs2, (s1, s2) -> {return s1.concat(s2);});

        res.subscribe(str -> {
            System.out.println(Thread.currentThread().getName() + " Received : " + str);
        }, throwable -> {
            printStackTrace();
            throwable.printStackTrace();
        });

    }

    static void printStackTrace() {

        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getLineNumber() + ")");
        }
    }
}
