package com.example.a305.splashscreen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class dataActivity extends AppCompatActivity {

    MqttAndroidClient mqttAndroidClient;
    final String serverUri = "tcp://ylowmv.messaging.internetofthings.ibmcloud.com:1883";
    String clientId = "a:ylowmv:ruuuterboyz";
    final String subscriptionTopic = "iot-2/type/its-car/id/306/evt/data/fmt/json";
    static final String mqttUsername = "a-ylowmv-lr1cz1ljzg";
    static final String mqttPassword = "TWyCAdjkEDG&B8mfbg\n";
    private String JSON_TEXT;
    private String temperature, humidity, state;

    private TextView temp = null;
    private TextView humi = null;
    private TextView sta = null;
    private ImageView mBackbutton;

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private int lastX = 0;
    private int lastX2 = 0;

    private double dtemp;
    private double dhumi;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_main);
        mBackbutton = (ImageView) findViewById(R.id.ic_backb);
        mp= MediaPlayer.create(dataActivity.this,R.raw.scream);

        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        series2 = new LineGraphSeries<DataPoint>();
        graph2.addSeries(series2);

        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(50);
        viewport.setScrollable(true);

        Viewport viewport2 = graph2.getViewport();
        viewport2.setYAxisBoundsManual(true);
        viewport2.setMinY(0);
        viewport2.setMaxY(50);
        viewport2.setScrollable(true);




        temp = (TextView) findViewById(R.id.temp_text);
        humi = (TextView) findViewById(R.id.humi_text);
        sta = (TextView) findViewById(R.id.sta_text);

        clientId = clientId + System.currentTimeMillis();

        // Create the client!
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);

        // CALLBACKS, these will take care of the connection if something unexpected happen

        mBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dataActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                    addToHistory("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                } else {
                    addToHistory("Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                addToHistory("The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                JSON_TEXT = new String(message.getPayload());
                addToHistory("Incoming message: " + new String(message.getPayload()));

                try {
                    // get JSONObject from JSON file
                    JSONObject obj = new JSONObject(JSON_TEXT);

                    // get location name and probability
                    temperature = obj.getString("temperature");
                    humidity = obj.getString("humidity");
                    state = obj.getString("state");

                    //Change temp to double for graph

                    // set location name and probability in TextView's
                    temp.setText("Temperature: "+temperature);
                    dtemp = Double.parseDouble(temperature);
                    addEntry(dtemp);
                    humi.setText("Humidity: "+humidity);
                    dhumi = Double.parseDouble(humidity);
                    addEntry2(dhumi);
                    sta.setText("Car state: "+state);

                    if(state.equals("OK"))
                    {

                    }
                    else if(state.equals("FLIPPED"))
                    {
                        if(mp.isPlaying() == false){
                            mp.start();
                        }

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // THIS VARIABLE IS THE JSON DATA. you can use GSON to get the needed
                // data (temperature for example) out of it, and show it in a textview or something else
                String result = new String(message.getPayload());
            }

            private void toString(byte[] payload) {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });



        // set up connection settings
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(mqttUsername);
        mqttConnectOptions.setPassword(mqttPassword.toCharArray());

        try {
            addToHistory("Connecting to " + serverUri);

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to connect to: " + serverUri);
                    addToHistory(exception.getMessage());
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100000; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry(dtemp);
                            addEntry2(dhumi);

                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }


    // add random data to graph
    private void addEntry(double d) {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, d ), false, 5);


    }
    private void addEntry2(double d) {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series2.appendData(new DataPoint(lastX2++, d ), false, 5);


    }

    // this could do something else, like update the new data to the layout!
    private void addToHistory(String mainText){
        Log.d("MYERROR", mainText);
    }

    // subscriber method
    public void subscribeToTopic(){
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                }
            });
        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }


}