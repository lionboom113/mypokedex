package jp.co.pise.projecttemplate_android.Presentation.View.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.TypeEntity;
import jp.co.pise.projecttemplate_android.Data.ValueObject.TemplateApplicationContext;
import jp.co.pise.projecttemplate_android.Domain.Models.TopFragmentModel;
import jp.co.pise.projecttemplate_android.Presentation.Event.Activity.MainActivityAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.Fragment.TopFragmentAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Presenter.Fragment.TopFragmentPresenter;
import jp.co.pise.projecttemplate_android.Presentation.View.Activity.PokemonDetailActivity;
import jp.co.pise.projecttemplate_android.Presentation.View.UIHelper.CommonUiHelper;
import jp.co.pise.projecttemplate_android.R;
import jp.co.pise.projecttemplate_android.databinding.TopFragmentBinding;

import static jp.co.pise.projecttemplate_android.Presentation.View.UIHelper.CommonUiHelper.GetTintedDrawable;

public class TopFragment extends Fragment {

    TopFragmentBinding binding;
    TopFragmentModel model;
    TopFragmentPresenter presenter;
    PokemonListAdapter pkmAdapter;
    LinearLayoutManager linearLayoutManager;
    Emitter<String> searchEmitter;
    int lastState = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.top_fragment, container, false);
        ((TextView) view.findViewById(R.id.titleLabel)).setText("POKéDEX");
        view.findViewById(R.id.notiButton).setOnClickListener((v) -> {
            if (view.findViewById(R.id.search_et).getVisibility() == View.GONE) {
                view.findViewById(R.id.search_et).setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.search_bar_pop_out);
                view.findViewById(R.id.search_et).startAnimation(anim);
            } else {
                view.findViewById(R.id.search_et).setVisibility(View.GONE);
                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.search_bar_pop_in);
                view.findViewById(R.id.search_et).startAnimation(anim);
            }

        });
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pkmRv);
        pkmAdapter = new PokemonListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        Stetho.initializeWithDefaults(this.getActivity());
        rv.setAdapter(pkmAdapter);


        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding = TopFragmentBinding.bind(getView());

        EditText searchText = (EditText) getView().findViewById(R.id.search_et);
        Observable<String> searchTextOb = Observable.create(emitter -> {
            searchEmitter = emitter;
            try {
                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        emitter.onNext(editable.toString());
                    }
                });

            } catch (Exception e) {
                emitter.onError(e);
                emitter.onComplete();
            }
        });
        presenter = new TopFragmentPresenter(searchTextOb);
        presenter.topFragment = this;

        binding.setModel(presenter.GetModel());
        binding.setFragment(this);
        pkmAdapter.datas = presenter.GetModel().pkms;
        pkmAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void RegistUser(View view){
        presenter.RegistUser(binding.name.getText().toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModelUpdate(TopFragmentAsyncEvent event)
    {
        if(event.IsSuccess())
        {
            if (event.eventType == TopFragmentAsyncEvent.EventType.LoadPokeComplete) {
                if (event.loadedPoke == null) {
                    pkmAdapter.notifyDataSetChanged();
                } else {
//                    if (((EditText)getView().findViewById(R.id.search_et)).getText().toString().equals("")) {
//                        pkmAdapter.notifyItemChanged(pkmAdapter.datas.size() - 1);
//                    } else {
//                        searchEmitter.onNext(((EditText)getView().findViewById(R.id.search_et)).getText().toString());
//                    }

                }
            }
            if (event.eventType == TopFragmentAsyncEvent.EventType.AddedPokeToList) {
                pkmAdapter.notifyItemInserted(pkmAdapter.datas.size() - 1);
                showSnackBar(event.loadedPoke.getName().toUpperCase() + " was added.");
            }
            if (event.eventType == TopFragmentAsyncEvent.EventType.PokemonTap) {
                onPokemonTap(event.view, event.loadedPoke);
            }
        }

        //EventBus.getDefault().post(new MainActivityAsyncEvent(MainActivityAsyncEvent.EventType.UserRegistComplete));
    }

    public static class PokemonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<PokemonEntity> datas;
        PokemonListAdapter() {
            datas = new ArrayList<PokemonEntity>();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public View cell;
            public ViewHolder(View v) {
                super(v);
                cell = v;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View textView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.poke_list_cell, parent, false);
            ViewHolder vh = new ViewHolder(textView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (datas == null || datas.get(position) == null) {
                return;
            }
            try {
                if (datas.get(position).getId() != 0) {
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvPkmName)).setText(datas.get(position).getName().toUpperCase());
                    String type1 = datas.get(position).types.get(0).type.getName();
                    String type2 = datas.get(position).types.size() > 1 ? datas.get(position).types.get(1).type.getName() : "";
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType)).setText(type1.toUpperCase());
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType)).setBackground(GetTintedDrawable(TemplateApplicationContext.Context.getResources(),
                            R.drawable.curl_border, Color.parseColor(CommonUiHelper.getTypeColor(type1))));
                    if (!type2.equals("")) {
                        ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType2)).setVisibility(View.VISIBLE);
                        ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType2)).setText(type2.toUpperCase());
                        ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType2)).setBackground(GetTintedDrawable(TemplateApplicationContext.Context.getResources(),
                                R.drawable.curl_border, Color.parseColor(CommonUiHelper.getTypeColor(type2))));
                    } else {
                        ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType2)).setVisibility(View.INVISIBLE);
                    }
                    //((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvDescription)).setText(datas.get(position).getDescriptions().get(0).getDescription());

                    ((ImageView)((ViewHolder)holder).cell.findViewById(R.id.ivPkm)).setImageBitmap(datas.get(position).image);
                    ((ViewHolder) holder).cell.setOnClickListener(v -> {
                        EventBus.getDefault().post(TopFragmentAsyncEvent.pokemonTap(datas.get(position), v));
                    });
                } else {
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvPkmName)).setText("取得中");
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType)).setText("...");
                    ((ImageView)((ViewHolder)holder).cell.findViewById(R.id.ivPkm)).setImageBitmap(null);
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType)).setBackgroundColor(Color.TRANSPARENT);
                    ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType2)).setBackgroundColor(Color.TRANSPARENT);
                }
            }
            catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }

        }



        @Override
        public int getItemCount() {
            return datas.size();
        }

//        public void add(String data) {
//            datas.add(data);
//        }

    }

    public void dataUpdate() {
        pkmAdapter.datas = presenter.GetModel().pkms;
        pkmAdapter.notifyDataSetChanged();
    }

    public void showSnackBar(String text) {
        Snackbar snackbar = Snackbar
                .make(getView(),text, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public void onPokemonTap(View view, PokemonEntity pokemonEntity) {
        Intent intent = new Intent(this.getActivity(), PokemonDetailActivity.class);
        intent.putExtra("id", pokemonEntity.getId() + "");

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), Pair.create(view.findViewById(R.id.ivPkm), "robot"),
                        Pair.create(view.findViewById(R.id.tvPkmName), "name"));

        startActivity(intent, options.toBundle());

    }

}
