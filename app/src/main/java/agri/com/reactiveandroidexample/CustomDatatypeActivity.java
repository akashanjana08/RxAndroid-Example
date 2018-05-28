package agri.com.reactiveandroidexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by akash.sharma on 5/28/2018.
 */

public class CustomDatatypeActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Observable<Student> studentObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentObservable = getStudentObservable();

        Observer<Student> studentObserver = new Observer<Student>() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast.makeText(CustomDatatypeActivity.this , "onSubscribe" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Student student) {

                Toast.makeText(CustomDatatypeActivity.this , student.name , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CustomDatatypeActivity.this , "onError" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(CustomDatatypeActivity.this , "onComplete" , Toast.LENGTH_SHORT).show();
            }
        };
        studentObservable.subscribe(studentObserver);
    }


    Observable<Student> getStudentObservable()
    {

        final List<Student> listStudent = getListStudentListData();


        return  Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {

                for (Student student : listStudent) {
                    if(!emitter.isDisposed()){
                        emitter.onNext(student);
                    }
                }

                if(!emitter.isDisposed())
                {
                    emitter.onComplete();
                }
            }
        });
    }





    List<Student> getListStudentListData()
    {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("1","Akash"));
        studentList.add(new Student("2","Gagan"));
        studentList.add(new Student("3","Alok"));
        studentList.add(new Student("4","Mohit"));
        return studentList;
    }
    class Student
    {
        private String id;
        private String name;

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
