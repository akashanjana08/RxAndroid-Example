package agri.com.reactiveandroidexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by akash.sharma on 5/28/2018.
 */

public class CompositeDisposalActivity extends AppCompatActivity{

    Observable<String> animalsObservable = Observable.just("Ant","Bee","Cat","Dog","Elephant");
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DisposableObserver<String> animalObserver = new DisposableObserver<String>() {
            @Override
            public void onComplete() {

                Toast.makeText(CompositeDisposalActivity.this , "Completed" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CompositeDisposalActivity.this , "Error" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(CompositeDisposalActivity.this , s , Toast.LENGTH_SHORT).show();
            }
        };


        DisposableObserver<String> animalsObserver = new DisposableObserver<String>() {
            @Override
            public void onComplete() {

                Toast.makeText(CompositeDisposalActivity.this , "Completed" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CompositeDisposalActivity.this , "Error" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(CompositeDisposalActivity.this , s , Toast.LENGTH_SHORT).show();
            }
        };

        compositeDisposable.add(animalsObservable
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toUpperCase().startsWith("D") || s.toUpperCase().startsWith("A");
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s.toUpperCase();
                    }
                })
                .subscribeWith(animalObserver));

        compositeDisposable.add(animalsObservable
                                  .subscribeWith(animalsObserver));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
