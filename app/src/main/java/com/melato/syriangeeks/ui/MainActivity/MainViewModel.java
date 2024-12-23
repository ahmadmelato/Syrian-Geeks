package com.melato.syriangeeks.ui.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.data.Working;
import com.melato.syriangeeks.model.AnswerModel;
import com.melato.syriangeeks.model.BlogDetalsModel;
import com.melato.syriangeeks.model.BlogModel;
import com.melato.syriangeeks.model.BookMarkModel;
import com.melato.syriangeeks.model.CertificateModel;
import com.melato.syriangeeks.model.CourseActivitiesModel;
import com.melato.syriangeeks.model.CourseDetalsModel;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.model.EventModel;
import com.melato.syriangeeks.model.LeaderBoardModel;
import com.melato.syriangeeks.model.MyCourseModel;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.model.QuestionModel;
import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();
    public MutableLiveData<Working> workingLoadMore = new MutableLiveData<>();
    public static MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CourseModel>> courseModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BlogModel>> blogModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<EventModel.Item>> eventModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CourseActivitiesModel.Datum>> courseActivitiesModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CertificateModel>> certificateModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<LeaderBoardModel>> leaderBoardModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BookMarkModel>> bookMarkModelModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<QuestionModel>> questionliveData = new MutableLiveData<>();
    public MutableLiveData<List<QuestionModel.Category>> categoryliveData = new MediatorLiveData<>();
    public MutableLiveData<List<AnswerModel>> answerliveData = new MutableLiveData<>();
    public MutableLiveData<ProfileModel> profileModelModelLiveData = new MutableLiveData<>();
    public MutableLiveData<BlogDetalsModel> blogdetailsModelLiveData = new MutableLiveData<>();
    public MutableLiveData<CourseDetalsModel> coursedetailsModelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<MyCourseModel.Datum>> myCourseModelLiveData = new MutableLiveData<>();

    public MainViewModel() {
        setProgressOK("");
        workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
    }

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
                    ClientAPI.setClientAPIToken("");
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
        ClientAPI.getClientAPI().getCourses(sortTag, 1).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    CourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("courses"), CourseModel.class);
                    if (courseModelLiveData.getValue() == null)
                        courseModelLiveData.setValue(new ArrayList<>());
                    List<CourseModel> courseModelList = courseModelLiveData.getValue();
                    courseModelList.add(courseModel);
                    courseModelLiveData.setValue(courseModelList);
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

    public void getCoursesMore(Context context, String sortTag) {
        Resources resources = context.getResources();
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        int page = 1;
        if (courseModelLiveData.getValue() != null) {
            page = courseModelLiveData.getValue().get(courseModelLiveData.getValue().size() - 1).current_page + 1;
        }
        ClientAPI.getClientAPI().getCourses(sortTag, page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    CourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("courses"), CourseModel.class);
                    if (courseModelLiveData.getValue() == null)
                        courseModelLiveData.setValue(new ArrayList<>());
                    List<CourseModel> courseModelList = courseModelLiveData.getValue();
                    courseModelList.add(courseModel);
                    courseModelLiveData.setValue(courseModelList);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getMoreBlogs(Context context) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        int page = 1;
        if (blogModelLiveData.getValue() != null) {
            page = blogModelLiveData.getValue().get(blogModelLiveData.getValue().size() - 1).current_page + 1;
        }
        ClientAPI.getClientAPI().getBlogs(page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    BlogModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blogs"), BlogModel.class);
                    if (blogModelLiveData.getValue() == null)
                        blogModelLiveData.setValue(new ArrayList<>());
                    List<BlogModel> blogModels = blogModelLiveData.getValue();
                    blogModels.add(blogModel);
                    blogModelLiveData.setValue(blogModels);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getBlogs(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        int page = 1;
        ClientAPI.getClientAPI().getBlogs(page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    BlogModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blogs"), BlogModel.class);
                    if (blogModelLiveData.getValue() == null)
                        blogModelLiveData.setValue(new ArrayList<>());
                    List<BlogModel> blogModels = blogModelLiveData.getValue();
                    blogModels.add(blogModel);
                    blogModelLiveData.setValue(blogModels);
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
        ClientAPI.getClientAPI().getCourses(sortTag, 1).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    CourseModel courseModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("courses"), CourseModel.class);
                    if (courseModelLiveData.getValue() == null)
                        courseModelLiveData.setValue(new ArrayList<>());
                    List<CourseModel> courseModelList = courseModelLiveData.getValue();
                    courseModelList.add(courseModel);
                    courseModelLiveData.setValue(courseModelList);
                    getIndexBlogs(context);
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
        ClientAPI.getClientAPI().getBlogs(1).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    BlogModel blogModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("blogs"), BlogModel.class);
                    if (blogModelLiveData.getValue() == null)
                        blogModelLiveData.setValue(new ArrayList<>());
                    List<BlogModel> blogModels = blogModelLiveData.getValue();
                    blogModels.add(blogModel);
                    blogModelLiveData.setValue(blogModels);
                    getIndexEvents(context);
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

    public void getCourseActivities(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourseActivities().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    CourseActivitiesModel courseActivitiesModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("enrolls"), CourseActivitiesModel.class);
                    courseActivitiesModelLiveData.setValue(courseActivitiesModel.data);
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

    public void getCertificate(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCertificate().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    Type certificateListType = new TypeToken<List<CertificateModel>>() {
                    }.getType();
                    List<CertificateModel> courseActivitiesModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("certificates"), certificateListType);
                    certificateModelLiveData.setValue(courseActivitiesModel);
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

    public void getLeaderBoard(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getLeaderBoard(1).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    LeaderBoardModel leaderBoardModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), LeaderBoardModel.class);
                    if (leaderBoardModelLiveData.getValue() == null)
                        leaderBoardModelLiveData.setValue(new ArrayList<>());
                    List<LeaderBoardModel> leaderBoardModels = leaderBoardModelLiveData.getValue();
                    leaderBoardModels.add(leaderBoardModel);
                    leaderBoardModelLiveData.setValue(leaderBoardModels);
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

    public void getLeaderBoardMore(Context context) {
        Resources resources = context.getResources();
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        int page = 1;
        if (leaderBoardModelLiveData.getValue() != null) {
            page = leaderBoardModelLiveData.getValue().get(leaderBoardModelLiveData.getValue().size() - 1).students.current_page + 1;
        }
        ClientAPI.getClientAPI().getLeaderBoard(page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    LeaderBoardModel leaderBoardModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), LeaderBoardModel.class);
                    if (leaderBoardModelLiveData.getValue() == null)
                        leaderBoardModelLiveData.setValue(new ArrayList<>());
                    List<LeaderBoardModel> leaderBoardModels = leaderBoardModelLiveData.getValue();
                    leaderBoardModels.add(leaderBoardModel);
                    leaderBoardModelLiveData.setValue(leaderBoardModels);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, "msg"));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
            }
        });
    }

    public void getBooMark(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getBooMark().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    getIndexEvents(context);
                    assert response.body() != null;
                    Type certificateListType = new TypeToken<List<BookMarkModel>>() {
                    }.getType();
                    List<BookMarkModel> courseActivitiesModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("bookmarks"), certificateListType);
                    bookMarkModelModelLiveData.setValue(courseActivitiesModel);
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
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getCourseFullDetails(id).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    CourseDetalsModel courseDetalsModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("details"), CourseDetalsModel.class);
                    Type listType = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    ArrayList<String> completed_lessons = new Gson().fromJson(response.body().getData().getAsJsonObject().get("enroll").getAsJsonObject().get("completed_lessons"), listType);
                    if (completed_lessons != null) {
                        for (String s_id : completed_lessons) {
                            for (CourseDetalsModel.Section section : courseDetalsModel.sections) {
                                for (CourseDetalsModel.Lesson lesson : section.lessons) {
                                    if (lesson.id == Integer.parseInt(s_id)) {
                                        lesson.is_completed = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    coursedetailsModelLiveData.setValue(courseDetalsModel);
                    setProgressOK(response.body().getMessage());
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

    public static void course_lecture_progress(Context context, String
            lString_code, List<String> strings) {
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().course_lecture_progress(lString_code, strings).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, ClientAPI.parseError(response).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                Toast.makeText(context, resources.getString(R.string.FailedtoloaddataChecknetwork), Toast.LENGTH_SHORT).show();
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
                    EventModel eventModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("events"), EventModel.class);
                    eventModelLiveData.setValue(eventModel.data);
                    setProgressOK(response.body().getMessage());
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

    public void getQuestions(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        int page = 1;
        if (questionliveData.getValue() != null && !questionliveData.getValue().isEmpty()) {
            page = questionliveData.getValue().get(questionliveData.getValue().size() - 1).questions.current_page + 1;
        }
        ClientAPI.getClientAPI().getQuestions(page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    QuestionModel questionModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), QuestionModel.class);
                    if (questionliveData.getValue() == null)
                        questionliveData.setValue(new ArrayList<>());
                    List<QuestionModel> questionModels = questionliveData.getValue();
                    questionModels.add(questionModel);
                    questionliveData.setValue(questionModels);
                    categoryliveData.setValue(questionModel.categories);
                    setProgressOK(response.body().getMessage());
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

    public void getMoreQuestions(Context context) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        int page = 1;
        if (questionliveData.getValue() != null) {
            page = questionliveData.getValue().get(questionliveData.getValue().size() - 1).questions.current_page + 1;
        }
        ClientAPI.getClientAPI().getQuestions(page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    QuestionModel questionModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), QuestionModel.class);
                    if (questionliveData.getValue() == null)
                        questionliveData.setValue(new ArrayList<>());
                    List<QuestionModel> questionModels = questionliveData.getValue();
                    questionModels.add(questionModel);
                    questionliveData.setValue(questionModels);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void getQuestionsDetails(Context context, Integer id) {
        setProgressRun("");
        Resources resources = context.getResources();
        int page = 1;
        if (answerliveData.getValue() != null && !answerliveData.getValue().isEmpty()) {
            page = answerliveData.getValue().get(answerliveData.getValue().size() - 1).current_page + 1;
        }
        ClientAPI.getClientAPI().getQuestionsDetails(id, page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    AnswerModel questionModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("answers"), AnswerModel.class);
                    if (answerliveData.getValue() == null)
                        answerliveData.setValue(new ArrayList<>());
                    List<AnswerModel> questionModels = answerliveData.getValue();
                    questionModels.add(questionModel);
                    answerliveData.setValue(questionModels);
                    setProgressOK(response.body().getMessage());
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

    public void getMoreQuestionsDetails(Context context, Integer id) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        int page = 1;
        if (answerliveData.getValue() != null) {
            page = answerliveData.getValue().get(answerliveData.getValue().size() - 1).current_page + 1;
        }
        ClientAPI.getClientAPI().getQuestionsDetails(id, page).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    AnswerModel questionModel = new Gson().fromJson(response.body().getData().getAsJsonObject().get("answers"), AnswerModel.class);
                    if (answerliveData.getValue() == null)
                        answerliveData.setValue(new ArrayList<>());
                    List<AnswerModel> questionModels = answerliveData.getValue();
                    questionModels.add(questionModel);
                    answerliveData.setValue(questionModels);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void comment(Context context, Integer id, String comment) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().comment(id, comment).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;

                    if (answerliveData.getValue() == null)
                        answerliveData.setValue(new ArrayList<>());
                    List<AnswerModel> questionModels = answerliveData.getValue();

                    for (AnswerModel answerModel : questionModels) {
                        for (AnswerModel.Datum item : answerModel.data) {

                        }
                    }
                    answerliveData.setValue(questionModels);
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void question_store(Context context, Integer category, String title, String question) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().question_store(category, title, question).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    questionliveData.setValue(new ArrayList<>());
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                    getQuestions(context);
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void question_store(Context context, Integer quesion_id, String answer) {
        workingLoadMore.setValue(new Working(ClientAPI.Run, ""));
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().answer_store(quesion_id, answer).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    answerliveData.setValue(new ArrayList<>());
                    workingLoadMore.setValue(new Working(ClientAPI.OK, ""));
                    getQuestionsDetails(context, quesion_id);
                } else {
                    workingLoadMore.setValue(new Working(ClientAPI.Deny, ClientAPI.parseError(response).getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                workingLoadMore.setValue(new Working(ClientAPI.Deny, resources.getString(R.string.FailedtoloaddataChecknetwork)));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getProfile(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().getProfile().enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    ProfileModel profileModel = new Gson().fromJson(response.body().getData().getAsJsonObject(), ProfileModel.class);
                    profileModelModelLiveData.setValue(profileModel);
                    setProgressOK(response.body().getMessage());
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


}
