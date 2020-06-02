package benefito.com.chatui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import benefito.com.chatui.GetterSetter.ContactVO;
import benefito.com.chatui.GetterSetter.OnlineUserUtil;

public class MainActivity extends AppCompatActivity {

    RecyclerView onlineUserRecyclerView,contactUserRecyclerView;
    List<OnlineUserUtil> onlineUserList = new ArrayList<>();
    List<ContactVO> contactUserList = new ArrayList<>();
    List<ContactVO> phoneContactVOList = new ArrayList<>();
    String url="https://uniqueandrocode.000webhostapp.com/hiren/androidweb.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        checkPermissions();

//        getOnlineUsers();
        getData();
        getAllContacts();

    }

    private void init() {

        onlineUserRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        contactUserRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_ver);
    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++){
                        JSONObject user_jsonObject=array.getJSONObject(i);
                        OnlineUserUtil userUtil=new OnlineUserUtil();
                        userUtil.setImageURL(user_jsonObject.getString("imageurl"));
//                        userUtil.setIs_online(user_jsonObject.getBoolean("online"));

                        onlineUserList.add(userUtil);
                    }
                    onlineUserRecyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager= new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    onlineUserRecyclerView.setLayoutManager(layoutManager);

                    OnlineUserAdapter adapter=new OnlineUserAdapter(onlineUserList,MainActivity.this);

                    onlineUserRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
//                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getOnlineUsers(List<ContactVO> phoneContactVOList) {

        try {
            JSONObject jsonObject= new JSONObject(ConstantJSON.JSON);
            JSONArray jsonArray_user = jsonObject.getJSONArray("data");
            for(int i= 0; i<jsonArray_user.length();i++)
            {
                JSONObject user_jsonObject= jsonArray_user.getJSONObject(i);
//                ContactVO contactVO= new ContactVO();
                phoneContactVOList.get(i).setContactImage(user_jsonObject.getString("image"));
                phoneContactVOList.get(i).setIs_online(user_jsonObject.getBoolean("is_online"));
                phoneContactVOList.get(i).setContactLastMessage(user_jsonObject.getString("last_message"));
                phoneContactVOList.get(i).setUnread_message_count(user_jsonObject.getString("unread_message_count"));
                phoneContactVOList.get(i).setLastMessageTime(user_jsonObject.getString("last_messsge_time"));

//                contactUserList.add(contactVO);

            }

            ContactsAdapter contactAdapter = new ContactsAdapter(phoneContactVOList, MainActivity.this,
                    getApplicationContext(), "");
            contactUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            contactUserRecyclerView.setAdapter(contactAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkPermissions(){
        PermissionUtil.askPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtil.PERMISSION_ALL:
            {
                if (grantResults.length > 0)
                {
                    List<Integer> indexesOfPermissionsNeededToShow = new ArrayList<>();
                    for(int i = 0; i < permissions.length; ++i) {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            indexesOfPermissionsNeededToShow.add(i);
                        }
                    }
                    int size = indexesOfPermissionsNeededToShow.size();
                    if(size != 0) {
                        int i = 0;
                        boolean isPermissionGranted = true;
                        while(i < size && isPermissionGranted) {
                            isPermissionGranted = grantResults[indexesOfPermissionsNeededToShow.get(i)]
                                    == PackageManager.PERMISSION_GRANTED;
                            i++;
                        }
//                        if(!isPermissionGranted)
//                        {
//                            showDialogNotCancelable("Permissions mandatory",
//                                    "All the permissions are required for this app",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            checkPermissions();
//                                        }
//                                    });
//                        }
                    }
                }
            }
        }
    }

    private void getAllContacts()
    {
        JSONArray postjson=new JSONArray();
        JSONObject json = null;
        ContactVO contactVO;
        phoneContactVOList.clear();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0)
                {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contactVO = new ContactVO();
                    contactVO.setContactName(name);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext())
                    {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber=phoneNumber.replace(" ","");
                        int size=phoneNumber.length();
                        if(phoneNumber.length()>=10)
                        {

                                phoneContactVOList.add(contactVO);
                        }
                    }
                    phoneCursor.close();
                }
            }

        }

        getOnlineUsers(phoneContactVOList);



    }
}
