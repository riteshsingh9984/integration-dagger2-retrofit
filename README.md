# integration-dagger2-retrofit
Dagger2 and Retrofit2 integration

### Dagger2 Integration

> Gradle dependencies required

```
    implementation 'com.google.dagger:dagger-android:2.35.1'
    implementation 'com.google.dagger:dagger-android-support:2.35.1' // if you use the support libraries
    annotationProcessor 'com.google.dagger:dagger-android-processor:2.35.1'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.35.1'
```

> In my case, Requirement is to create all instances of those classes which are being used to complete all activities functionalities.
> So I just create them via dagger2 and why this, because I want to take care them by dagger2 and sharable across all activities.
> Here , Sample how to create via using dagger2 and how module, component and activity communicate to each-other.

>My Class which will create by dagger 

```
package com.example.shiv.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.shiv.myapplication.R;
import com.example.shiv.myapplication.config.ApiClient;
import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.repositories.UserRepository;
import com.example.shiv.myapplication.utils.Constants;
import com.example.shiv.myapplication.utils.LoginUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {

    public AuthService(){ }

    public String getTest(String str){
        return str+"test me";
    }

    public Map<String , String> login(User user){

        Map<String, String> loginResult = new HashMap<>();
        loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_FAILED);
        loginResult.put(Constants.ERROR , Constants.LOGIN_ATTRIBUTE.USERNAME_PASSWORD_INCORRECT);

        if(user.getUsername().equals("test")){
            SessionManager.INSTANCE.setUser(user);
            loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_SUCCESS);

            Log.i("login", "API-User: "+user);
        }

        return loginResult;
    }

}

```

> And below class also wil be created by dagger2

```
package com.example.shiv.myapplication.services;

import android.util.Log;

public class UserService {

    public void userTest(){
        Log.i("User-Service", "user test service accessed");
    }
}

```

> Let's create Dagger2 Module and Provider for both above classes

```
package com.example.shiv.myapplication.modules;

import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    @Provides
    @Singleton
    public AuthService getAuthService(){
        return new AuthService();
    }

    @Provides
    @Singleton
    public UserService getUserService(){
        return new UserService();
    }
}

```

> Now Dagger2 Module and provider is ready , So let's create Dagger2 Component(This will supply my instances).

```
package com.example.shiv.myapplication.components;

import com.example.shiv.myapplication.modules.ServicesModule;
import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component( modules = {ServicesModule.class})
public interface ApplicationComponents {

    public AuthService getAuthService();
    public UserService getUserService();

}
```

> Now you are ready to use 

```
   ApplicationComponents applicationComponents = DaggerApplicationComponents.builder().build();
   AuthService authService = applicationComponents.getAuthService();
   UserService userService = applicationComponents.getUserService();
```

### Retrofit2 Integration

> Gradle dependencies required

```
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.7.2' // if wants to debug only
    implementation 'com.squareup.okhttp3:okhttp:4.7.2'
```

> I am show sample for below data modal

```
package com.example.shiv.myapplication.modals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Inject;

public class User implements Serializable {

    private static final long serialVersionUID = -8283916825882093630L;

    private String id;
    private String username;

    private String code;

    @Inject
    public User(){}

    public User(String id, String username, String code) {
        this.id = id;
        this.username = username;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username != null && user.username != null &&
                this.username.equals(user.username);
    }

    @Override
    public int hashCode() {

        if(this.username != null ){
            return this.username.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

```

> Retrofit2 instance creation

```
package com.example.shiv.myapplication.config;

import com.example.shiv.myapplication.utils.APIConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader(APIConstants.API_CONTENT_KEY, APIConstants.API_CONTENT_VALUE)
                        .build();

                return chain.proceed(newRequest);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)   // for debugg purpose
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.API_BASE)      // REST API entry in my case : http://192.168.1.12:8099/rest-api/
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}

```

> Repository for Retrofit2

```
package com.example.shiv.myapplication.repositories;

import com.example.shiv.myapplication.modals.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserRepository {

    @GET("rest/user/{username}")
    Call<User> findUserByUsername(@Path("username") String username);
}

```

> Now just add below code in manifest.xml for allow internet 

```
<manifest ...>

<uses-permission android:name="android.permission.INTERNET" />
<manifest>
```
> Now, you should need to clear text traffic to avoid expcetion , So again go into manifest.xml and set 'android:usesCleartextTraffic="true"' 
> in application tag like below

```
<manifest ...>

<application  ... android:usesCleartextTraffic="true">
...
...
</application>

<uses-permission android:name="android.permission.INTERNET" />
<manifest>
```

> Now, Everything done , Let's make call via Retrofit2

```
Call<User> userByUsernameCall = userRepository.findUserByUsername(user.getUsername());

// Async API Call
userByUsernameCall.enqueue(new Callback<User>() {
    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if(response.isSuccessful()) {
            User user = response.body();
        }
    }
    @Override
    public void onFailure(Call<User> call, Throwable t) {
        call.cancel();
    }
});
```

> Retrofit2 enqueue being used for Async api calls if you wants to make Sync then call execute instead of enqueue like below

```
Call<User> userByUsernameCall = userRepository.findUserByUsername(user.getUsername());

Response<User> userResponse = userByUsernameCall.execute();

if(userResponse.isSuccessful()){
  User user = response.body();
}

```

> Avoid to use Sync calls because it blocks user's interaction with UI.

> Thanks
