package com.luohe.android.luohe.luohe;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luohe.android.luohe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HowToPlayActivity extends Activity {

    @Bind(R.id.one)
    TextView one;
    @Bind(R.id.two)
    TextView two;
    @Bind(R.id.three)
    TextView three;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.commit)
    TextView commit;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        ButterKnife.bind(this);
       initView();
    }

    private void initView() {
        commit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (count) {
                    case 0:
                        title.setText("落和令怎么玩？");
                        one.setText("     户间可以通过“祝愿池”相互赠送才值。才值的赠送可以选择匿名噢。");
                        two.setText("      才值是落和平台的积分体系，使落和的各个功能产生联动。每个用户在注册后有初始的100才值。用户可以通过发布落和令回答落友们感兴趣的问题赚取才值。当然同样的，当你像你感兴趣的才者提出问题，并收到了他/她的回答时，则将消耗你悬赏的等额才值。");
                        three.setText("     每个才者发布的内容，无论是文风还是落和令问令所回答的问题，每收到一个红心“喜欢”，才者获得1才值。落友们点取红心“喜欢”不消耗自身的才值。");
                        commit.setText("下一步");
                        count+=1;
                        break;
                    case 1:
                        title.setText("怎么玩？");
                        one.setText("    用户间可以通过“祝愿池”相互赠送才值。才值的赠送可以选择匿名噢。");
                        two.setText("      每个用户新拉来一名注册用户，除了可以获得1元的现金返利后，也可以额外获得2才值。");
                        three.setText("     每周五晚上6点的排行榜，你都可以看到关于各种才值排名的最新更新。包括“累计才值榜”、“最快成长榜”和“好有才值榜”。查看这里就可以看到你的江湖排名哦～");
                        commit.setText("下一步");
                        count+=1;
                        break;
                    case 2:
                        title.setText("这么玩？");
                        one.setText("    你可以通过“我—召唤”按钮将落和注册链接分享出去。通过你的链接，每注册一名新的用户，你将会收到1元钱的现金返利。现金返利无上限。");
                        two.setText("      你发布的内容，无论是落和令回答的文章还是发布的文风，每当有1次的浏览量，都会收到1分钱的现金返利。现金返利同样无上限。");
                        three.setText("     落友们可通过“我—提现”选项，通过支付宝提取现金。现今提取会有一天的延迟。机会多多，还不快来参与～");
                        commit.setText("知道了");
                        count+=1;
                        break;
                    case 3:
                        finish();
                        break;
                }
            }
        });
    }
}
