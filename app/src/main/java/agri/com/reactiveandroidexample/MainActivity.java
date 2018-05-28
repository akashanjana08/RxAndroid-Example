package agri.com.reactiveandroidexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    Observable<String> animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observer<String> animalsObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable= d;
                Toast.makeText(MainActivity.this , "Subscribe" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

                Toast.makeText(MainActivity.this , "Completed" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this , "Error" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this , s , Toast.LENGTH_SHORT).show();
            }
        };


        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("d")||s.toLowerCase().startsWith("b");
                    }
                })
                .subscribe(animalsObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
