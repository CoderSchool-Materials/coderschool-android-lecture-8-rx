package com.kungfoolabs.lecture8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

public class MainActivity extends AppCompatActivity {

    private Disposable formCompletionSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitialValueObservable<CharSequence> nameChanged = RxTextView.textChanges((EditText) findViewById(R.id.name));
        InitialValueObservable<CharSequence> phoneChanged = RxTextView.textChanges((EditText) findViewById(R.id.phone));
        InitialValueObservable<CharSequence> emailChanged = RxTextView.textChanges((EditText) findViewById(R.id.email));

        formCompletionSubscription = Observable.combineLatest(nameChanged, phoneChanged, emailChanged, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                return (charSequence.length() > 0) && (charSequence2.length() > 0) && (charSequence3.length() > 0);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                ((Button) findViewById(R.id.button)).setEnabled(aBoolean);
            }
        });
    }
}
