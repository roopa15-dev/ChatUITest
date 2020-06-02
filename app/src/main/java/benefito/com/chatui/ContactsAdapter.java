package benefito.com.chatui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import benefito.com.chatui.GetterSetter.ContactVO;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Benefito on 28-04-2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>{

    private List<ContactVO> contactVOList;
    private Context mContext;
    Activity ContactActivity;
    String message,basechat;


    public ContactsAdapter(List<ContactVO> contactVOList, Activity ContactActivity, Context mContext, String message){
        this.contactVOList = contactVOList;
        this.ContactActivity=ContactActivity;
        this.mContext = mContext;
        this.message=message;
        this.basechat = basechat;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position)
    {
        final ContactVO contactVO = contactVOList.get(position);
        try
        {


            holder.tvContactName.setText(contactVO.getContactName());
            holder.tvLastMessage.setText(contactVO.getContactLastMessage());
            holder.tvLastMessageTime.setText(contactVO.getLastMessageTime());

            String val=contactVO.getUnread_message_count();
            if(contactVO.getUnread_message_count() == null)
            {
                holder.ivContactImageCount.setVisibility(View.GONE);
                holder.tvLastMessageCount.setVisibility(View.GONE);
            }
            else if(contactVO.getUnread_message_count().equals("0"))
            {
                holder.ivContactImageCount.setVisibility(View.VISIBLE);
                holder.tvLastMessageCount.setVisibility(View.GONE);

            }
            else {
                holder.ivContactImageCount.setVisibility(View.GONE);
                holder.tvLastMessageCount.setVisibility(View.VISIBLE);
                holder.tvLastMessageCount.setText(contactVO.getUnread_message_count());
            }

            String user_profile=contactVO.getContactImage();

            if (user_profile == null) {
                holder.ivContactImage.setImageResource(R.drawable.default_profile);
            }
            else if(user_profile.equals(""))
            {
                holder.ivContactImage.setImageResource(R.drawable.default_profile);
            }
            else
            {
                Picasso.Builder builder = new Picasso.Builder(mContext);
                builder.listener(new Picasso.Listener()
                {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                    {
                        holder.ivContactImage.setImageResource(R.drawable.default_profile);
                    }
                });
                builder.build().load(user_profile).into(holder.ivContactImage);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        CircleImageView ivContactImage;
        TextView tvContactName;
        TextView tvLastMessage,tvLastMessageTime,tvLastMessageCount;
        RelativeLayout rlContactContainer;
        ImageView ivContactImageCount;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ivContactImage = (CircleImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvLastMessage = (TextView) itemView.findViewById(R.id.tvLastMessage);
            tvLastMessageTime = (TextView) itemView.findViewById(R.id.textViewLastMessageTime);
            tvLastMessageCount = (TextView) itemView.findViewById(R.id.textViewLastMessagecount);
            rlContactContainer = (RelativeLayout) itemView.findViewById(R.id.contact_container);
            ivContactImageCount = (ImageView) itemView.findViewById(R.id.ImgLastMessagecount);
        }
    }
}