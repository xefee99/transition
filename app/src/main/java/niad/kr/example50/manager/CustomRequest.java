package niad.kr.example50.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 */
public class CustomRequest extends StringRequest {

    private Map<String, String> params;

    private Map<String, String> headers;

    public CustomRequest(String url, Response.Listener<String> reponseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, reponseListener, errorListener);
    }

    public CustomRequest(String url, Map<String, String> headers, Map<String, String> params, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        this(url, responseListener, errorListener);
        this.headers = headers;
        this.params = params;
    }

    public CustomRequest(int method, String url, Response.Listener<String> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, reponseListener, errorListener);
    }

    public CustomRequest(int method, String url, Map<String, String> headers, Map<String, String> params, Response.Listener<String> reponseListener, Response.ErrorListener errorListener) {
        this(method, url, reponseListener, errorListener);
        this.headers = headers;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams()  throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }
}
