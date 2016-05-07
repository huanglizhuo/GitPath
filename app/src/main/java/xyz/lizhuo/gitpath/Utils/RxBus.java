package xyz.lizhuo.gitpath.Utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by lizhuo on 16/4/15.
 */
public class RxBus {
    private final Subject<Object,Object> bus = new SerializedSubject<>(PublishSubject.create());
    private static volatile RxBus mDefaultInstance;

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    public void send(Object o){
        bus.onNext(o);
    }

    public Observable<Object> toObservable(){
        return bus;
    }

    public boolean hasObservers(){
        return bus.hasObservers();
    }
}
