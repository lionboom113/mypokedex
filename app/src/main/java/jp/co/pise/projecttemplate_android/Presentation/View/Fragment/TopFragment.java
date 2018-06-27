package jp.co.pise.projecttemplate_android.Presentation.View.Fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.TypeEntity;
import jp.co.pise.projecttemplate_android.Domain.Models.TopFragmentModel;
import jp.co.pise.projecttemplate_android.Presentation.Event.Activity.MainActivityAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.Fragment.TopFragmentAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Presenter.Fragment.TopFragmentPresenter;
import jp.co.pise.projecttemplate_android.R;
import jp.co.pise.projecttemplate_android.databinding.TopFragmentBinding;

public class TopFragment extends Fragment {

    TopFragmentBinding binding;
    TopFragmentModel model;
    TopFragmentPresenter presenter;
    PokemonListAdapter pkmAdapter;
    LinearLayoutManager linearLayoutManager;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.top_fragment, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pkmRv);
        pkmAdapter = new PokemonListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(pkmAdapter);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = TopFragmentBinding.bind(getView());
        presenter = new TopFragmentPresenter();
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

    @Subscribe
    public void onModelUpdate(TopFragmentAsyncEvent event)
    {
        if(event.IsSuccess())
        {
            Toast.makeText(this.getActivity(), "成功！", Toast.LENGTH_SHORT).show();
        }

        EventBus.getDefault().post(new MainActivityAsyncEvent(MainActivityAsyncEvent.EventType.UserRegistComplete));
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
            if (datas.get(position).getId() != 0) {
                ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvPkmName)).setText(datas.get(position).getName());
                String type = "";
                for (TypeEntity te: datas.get(position).getTypes()
                     ) {
                    type = type + te.getType().getName() + " ";
                }
                ((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvType)).setText(type);
                //((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvDescription)).setText(datas.get(position).getDescriptions().get(0).getDescription());

                try {
                    URL url = null;
                    url = new URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ (position + 1) + ".png");
                    new DownloadImageTask(((ImageView)((ViewHolder)holder).cell.findViewById(R.id.ivPkm)), datas.get(position)).execute(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        PokemonEntity pokemonEntity;

        public DownloadImageTask(ImageView bmImage, PokemonEntity pokemonEntity) {
            this.pokemonEntity = pokemonEntity;
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            if (pokemonEntity.image == null) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                pokemonEntity.image = mIcon11;
                return mIcon11;
            } else {
                return pokemonEntity.image;
            }

        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
