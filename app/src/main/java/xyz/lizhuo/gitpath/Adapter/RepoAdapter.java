package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.util.List;

import xyz.lizhuo.gitpath.GitPathApplication;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.GlideManager;

/**
 * Created by lizhuo on 16/4/8.
 */
public class RepoAdapter extends BaseAdapter {
    public List<Repo> list;

    public RepoAdapter(Context context, List<Repo> repos) {
        super(context);
        this.list = repos;
    }

    @Override
    public int onCreateViewLayout(int viewType) {
        return R.layout.item_repo_layout;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        Repo repo = list.get(position);
        baseViewHolder.getTextView(R.id.repo_name_tv).setText(repo.getName());
        baseViewHolder.getTextView(R.id.repo_ownername_tv).setText(repo.getOwner().getLogin());
        baseViewHolder.getTextView(R.id.repo_desciption_tv).setText("    " + repo.getDescription());
        baseViewHolder.getTextView(R.id.stars_num_tv).setText(repo.getStargazers_count() + "");
        baseViewHolder.getTextView(R.id.fork_num_tv).setText(repo.getForks_count() + "");


        ImageView avatar = baseViewHolder.getImageView(R.id.avatar_spv);
        GlideManager.getInstance().loadCircleImage(context, repo.getOwner().getAvatar_url(), avatar);

        // TODO: 16/5/8  add language type image
        baseViewHolder.getImageView(R.id.language_img)
                .setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.language));
        if (repo.getLanguage() == null) {
            baseViewHolder.getTextView(R.id.language_tv).setText("unknow");
        } else {
            baseViewHolder.getTextView(R.id.language_tv).setText(repo.getLanguage().toString());
        }
//        if (repo.getLanguage()==null){
//            baseViewHolder.getTextView(R.id.language_tv).setText("unknow");
//            baseViewHolder.getImageView(R.id.language_img)
//                    .setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.language));
//
//        }else if (repo.getLanguage()=="go"){
//            baseViewHolder.getTextView(R.id.language_tv).setText("unknow");
//            baseViewHolder.getImageView(R.id.language_img)
//                    .setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.language));
//        }
    }

    public void refresh(List<Repo> repos) {
        list = null;
        list = repos;
        notifyDataSetChanged();
    }

    public void update(List<Repo> repos) {
        list.addAll(repos);
        notifyDataSetChanged();
        if (repos != null && repos.size() > 0) {
            int startPosition = list.size();
            list.addAll(repos);
            notifyItemRangeInserted(startPosition, repos.size());
        }
    }
}
