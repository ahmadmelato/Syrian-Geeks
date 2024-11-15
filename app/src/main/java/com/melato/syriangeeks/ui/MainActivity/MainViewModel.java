package com.melato.syriangeeks.ui.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.data.Working;
import com.melato.syriangeeks.model.BlogDetalsModel;
import com.melato.syriangeeks.model.BlogModel;
import com.melato.syriangeeks.model.CourseDetalsModel;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.model.EventModel;
import com.melato.syriangeeks.model.MyCourseModel;
import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();
    public static MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CourseModel.Datum>> courseModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BlogModel.Blog>> blogModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<EventModel.Item>> eventModelLiveData = new MutableLiveData<>();

    public MutableLiveData<BlogDetalsModel> blogdetailsModelLiveData = new MutableLiveData<>();
    public MutableLiveData<CourseDetalsModel> coursedetailsModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<MyCourseModel.Datum>> myCourseModelLiveData = new MutableLiveData<>();


    private void setProgressOK(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.OK, msg));
        }
    }

    private void setProgressFiled(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Filed, msg));
        }
    }

    private void setProgressDeny(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Deny, msg));
        }
    }

    private void setProgressRun(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Run, msg));
        }
    }

    public void logout(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().logout().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    userLiveData.setValue(null);
                    saveData(context, null);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getCourses(Context context, String sortTag) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourses(sortTag).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    CourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("courses"), CourseModel.class);
                    courseModelLiveData.setValue(courseModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getMyCourses(Context context, String sortTag) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getMyCourses(sortTag).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    MyCourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), MyCourseModel.class);
                    myCourseModelLiveData.setValue(courseModel.enrolls.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getIndexCourses(Context context, String sortTag) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourses(sortTag).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    getIndexBlogs(context);
                    assert response.body() != null;
                    CourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("courses"), CourseModel.class);
                    courseModelLiveData.setValue(courseModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getBlogs(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getBlogs().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    BlogModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blogs"), BlogModel.class);
                    blogModelLiveData.setValue(blogModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getIndexBlogs(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getBlogs().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    getIndexEvents(context);
                    assert response.body() != null;
                    BlogModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blogs"), BlogModel.class);
                    blogModelLiveData.setValue(blogModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getCourseDetails(Context context, Integer id) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourseDetails(id).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    CourseDetalsModel courseDetalsModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("details"), CourseDetalsModel.class);
                    coursedetailsModelLiveData.setValue(courseDetalsModel);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void join(Context context, int id, String slag) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().join(slag).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    getCourseDetails(context, id);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getCourseFullDetails(Context context, Integer id) {
        setProgressRun("");
        System.out.println(id + " sss");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourseFullDetails(id).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    CourseDetalsModel courseDetalsModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("details"), CourseDetalsModel.class);
                    coursedetailsModelLiveData.setValue(courseDetalsModel);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void getBlogsDetails(Context context, Integer id) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getBlogsDetails(id).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    BlogDetalsModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blog"), BlogDetalsModel.class);
                    blogdetailsModelLiveData.setValue(blogModel);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getEvents(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getEvents().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    EventModel eventModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("events"), EventModel.class);
                    eventModelLiveData.setValue(eventModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getIndexEvents(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getEvents().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    EventModel eventModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("events"), EventModel.class);
                    eventModelLiveData.setValue(eventModel.data);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void saveData(Context context, UserModel userModel) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsLoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("loginInfo", new Gson().toJson(userModel));
        editor.apply();
    }

    public void getData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsLoginData", Context.MODE_PRIVATE);
        String loginInfo = preferences.getString("loginInfo", null);
        UserModel userModel = new Gson().fromJson(loginInfo, UserModel.class);
        if (userModel != null) {
            userLiveData.setValue(userModel);
        }
    }
}
