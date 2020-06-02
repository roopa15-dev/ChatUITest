package benefito.com.chatui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import benefito.com.chatui.GetterSetter.OnlineUserUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class OnlineUserAdapter extends RecyclerView.Adapter<OnlineUserAdapter.ViewHolder>{

    List<OnlineUserUtil> list_data;
    Context context;

    public OnlineUserAdapter(List<OnlineUserUtil> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }
    @NonNull
    @Override
    public OnlineUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineUserAdapter.ViewHolder holder, int position) {

        OnlineUserUtil listData=list_data.get(position);

        Picasso.with(context)
                .load(listData
                        .getImageURL())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imageView;
//        public TextView pJobProfile;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
//            pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    PersonUtils cpu = (PersonUtils) view.getTag();
//
//                    Toast.makeText(view.getContext(), cpu.getPersonFirstName()+" "+cpu.getPersonLastName()+" is "+ cpu.getJobProfile(), Toast.LENGTH_SHORT).show();
//
//                }
//            });

        }
    }
}
