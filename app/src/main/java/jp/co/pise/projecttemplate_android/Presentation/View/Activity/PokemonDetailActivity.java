package jp.co.pise.projecttemplate_android.Presentation.View.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jp.co.pise.projecttemplate_android.Data.ValueObject.TemplateApplicationContext;
import jp.co.pise.projecttemplate_android.Presentation.Event.Activity.PokemonDetailAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.Fragment.TopFragmentAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Presenter.PokemonDetailPresenter;
import jp.co.pise.projecttemplate_android.Presentation.View.Fragment.TopFragment;
import jp.co.pise.projecttemplate_android.Presentation.View.UIHelper.CommonUiHelper;
import jp.co.pise.projecttemplate_android.R;

import static jp.co.pise.projecttemplate_android.Presentation.View.UIHelper.CommonUiHelper.GetTintedDrawable;

public class PokemonDetailActivity extends AppCompatActivity {
    PokemonDetailPresenter presenter;
    String targetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        targetId = getIntent().getStringExtra("id");
        presenter = new PokemonDetailPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        presenter.loadId(targetId);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModelUpdate(PokemonDetailAsyncEvent event)
    {
        if (event.IsSuccess()) {
            ((TextView)(findViewById(R.id.tvPkmName))).setText(presenter.model.getName().toUpperCase());
            String type1 = presenter.model.types.get(0).type.getName();
            String type2 =presenter.model.types.size() > 1 ?presenter.model.types.get(1).type.getName() : "";
            ((TextView)findViewById(R.id.tvType)).setText(type1.toUpperCase());
            ((TextView)findViewById(R.id.tvType)).setBackground(GetTintedDrawable(TemplateApplicationContext.Context.getResources(),
                    R.drawable.curl_border, Color.parseColor(CommonUiHelper.getTypeColor(type1))));
            if (!type2.equals("")) {
                ((TextView)findViewById(R.id.tvType2)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.tvType2)).setText(type2.toUpperCase());
                ((TextView)findViewById(R.id.tvType2)).setBackground(GetTintedDrawable(TemplateApplicationContext.Context.getResources(),
                        R.drawable.curl_border, Color.parseColor(CommonUiHelper.getTypeColor(type2))));
            } else {
                ((TextView)findViewById(R.id.tvType2)).setVisibility(View.INVISIBLE);
            }
            //((TextView)((ViewHolder)holder).cell.findViewById(R.id.tvDescription)).setText(datas.get(position).getDescriptions().get(0).getDescription());

            ((ImageView)findViewById(R.id.ivPkm)).setImageBitmap(presenter.model.image);
        }
    }
}
