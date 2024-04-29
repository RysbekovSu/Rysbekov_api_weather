    package com.example.rysbekov_api.ui.home;

    import android.annotation.SuppressLint;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.airbnb.lottie.LottieAnimationView;
    import com.example.rysbekov_api.R;
    import com.example.rysbekov_api.databinding.FragmentHomeBinding;
    import com.example.rysbekov_api.models.Clouds;
    import com.example.rysbekov_api.models.Main;
    import com.example.rysbekov_api.models.Model;
    import com.example.rysbekov_api.models.Sys;
    import com.example.rysbekov_api.models.Wind;
    import com.example.rysbekov_api.remote_data.RetrofitBuilder;
    import com.example.rysbekov_api.remote_data.WeatherApi;

    import java.util.Date;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class HomeFragment extends Fragment {

        private FragmentHomeBinding binding;
        LottieAnimationView rainy_lotty;
        Integer temperature;
        Integer tempMAx;
        Integer tempMIN;
        String c_time = java.text.DateFormat.getDateTimeInstance().format(new Date());
        int humidity_c;
        final String apiKey = WeatherApi.URL;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            binding.rainLotty.setAnimation(R.raw.rain);

            binding.localtime.setText(c_time);




            Call<Model> call = RetrofitBuilder.getInstance().getCurrentWeather("Bishkek", apiKey);

            call.enqueue(new Callback<Model>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        Main main_model = response.body().getMain_model();
                        Wind wind_model = response.body().getWind_model();

                        Clouds clouds_model = response.body().getClouds_model();

                        Sys sys_model = response.body().getSys_model();

                        Double temp = main_model.getTemp();
                        Double tempMaxi = main_model.getTempMax();
                        Double tempMini = main_model.getTempMin();

                        temperature = makeFormFaringate(temp);
                        tempMAx = makeFormFaringate(tempMaxi);
                        tempMIN = makeFormFaringate(tempMini);

                        binding.tempC.setText(temperature + " °C");
                        if(temperature<=14){
                            binding.sun.setVisibility(View.INVISIBLE);
                            setNoHotWeather();
                        }


                        binding.maxMinTemp.setText(tempMAx + " °C↑  \n" + tempMIN + " °C↓");

                        binding.cityName.setText("Bishkek");

                        binding.humidity.setText(main_model.getHumidity() + " %");
                        humidity_c = main_model.getHumidity();
                        if(humidity_c >= 55){
                            rainy_possible();
                        }

                        binding.pressure.setText(main_model.getPressure() + " \nmBar");

                        binding.cloud.setText(clouds_model.getAll() + " \nmBar");
                        binding.wind.setText(wind_model.getSpeed() + " m/s");

                        binding.sunrise.setText(getCurrDateTime(sys_model.getSunrise()));
                        binding.sunset.setText(getCurrDateTime(sys_model.getSunset()));
                        binding.timezone.setText(String.valueOf(response.body().getTimezone()));
                        if(response.body().getTimezone() <= 6500 && response.body().getTimezone() >= -27500){
                            setNight();
                        }else {
                            setDay();
                        }
                    }
                    setCondition();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable throwable) {
                    Toast.makeText(requireActivity(), "NO data" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return root;
        }




        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            binding.slideUpBottomSheet.setOnClickListener(v -> {
                if (binding.bottomSheet.getVisibility() == View.GONE) {

                    binding.bottomSheet.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomSheet.setVisibility(View.GONE);
                }
                binding.rainLotty.setVisibility(View.INVISIBLE);
                binding.blueSky.setVisibility(View.VISIBLE);
                binding.sun.setVisibility(View.INVISIBLE);
                binding.badWeatherSky.setVisibility(View.INVISIBLE);
                binding.inputCity.setText("");
                binding.condition.setText("...");
//                binding.isRainOrNot.setVisibility(View.INVISIBLE);
            });

            binding.search.setOnClickListener(v1 -> {
                    if (!binding.inputCity.getText().toString().isEmpty()) {

                        Call<Model> call = RetrofitBuilder.getInstance().getCurrentWeather(binding.inputCity.getText().toString(), apiKey);
                        call.enqueue(new Callback<Model>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<Model> call,@NonNull Response<Model> response) {
                                Main main_model = response.body().getMain_model();
                                Wind wind_model = response.body().getWind_model();

                                Clouds clouds_model = response.body().getClouds_model();

                                Sys sys_model = response.body().getSys_model();

                                Double temp = main_model.getTemp();
                                Double tempMaxi = main_model.getTempMax();
                                Double tempMini = main_model.getTempMin();

                                temperature = makeFormFaringate(temp);
                                tempMAx = makeFormFaringate(tempMaxi);
                                tempMIN = makeFormFaringate(tempMini);

                                binding.tempC.setText(temperature + " °C");
                                if(temperature<=14){
                                    binding.sun.setVisibility(View.INVISIBLE);
                                    setNoHotWeather();
                                }

                                binding.maxMinTemp.setText(tempMAx + " °C↑  \n" + tempMIN + " °C↓");

                                binding.cityName.setText(binding.inputCity.getText().toString());

                                binding.humidity.setText(main_model.getHumidity() + " %");
                                humidity_c = main_model.getHumidity();
                                if(humidity_c >= 55){
                                    rainy_possible();
                                }
                                binding.pressure.setText(main_model.getPressure() + " \nmBar");

                                binding.cloud.setText(clouds_model.getAll() + " \nmBar");
                                binding.wind.setText(wind_model.getSpeed() + " m/s");

                                binding.sunrise.setText(getCurrDateTime(sys_model.getSunrise()));
                                binding.sunset.setText(getCurrDateTime(sys_model.getSunset()));
                                binding.timezone.setText(String.valueOf(response.body().getTimezone()));
                                if(response.body().getTimezone() <= 6500 && response.body().getTimezone() >= -27500){
                                    setNight();
                                }else {
                                    setDay();
                                }
                                setCondition();

                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Toast.makeText(requireActivity(), "No WeatherForeCast data"+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        binding.bottomSheet.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(requireActivity(), "Input the name of the city!!!", Toast.LENGTH_SHORT).show();
                    }
                });




        }
        public  void setDay(){
            binding.nightSky.setVisibility(View.INVISIBLE);
            binding.blueSky.setVisibility(View.VISIBLE);
        }
        public  void setNight(){
            binding.nightSky.setVisibility(View.VISIBLE);
            binding.blueSky.setVisibility(View.INVISIBLE);
            binding.sun.setVisibility(View.INVISIBLE);

        }
        public  void rainy_possible(){
            binding.isRainyOrNot.setVisibility(View.VISIBLE);
            binding.isRainyOrNot.setText("rain is \npossible ");
            binding.rainLotty.setVisibility(View.VISIBLE);
            binding.sun.setVisibility(View.INVISIBLE);
            binding.badWeatherSky.setVisibility(View.VISIBLE);
        }
        public  void dryWeather(){
            binding.rainLotty.setVisibility(View.INVISIBLE);
            binding.blueSky.setVisibility(View.VISIBLE);
            binding.sun.setVisibility(View.VISIBLE);
            binding.badWeatherSky.setVisibility(View.INVISIBLE);
        }

        public int makeFormFaringate(double tt) {
            Integer gr = (int) (tt - 273.15);
            return gr;
        }

        public void setCondition() {
            if (temperature >= 20 && temperature<=50) {
                binding.blueSky.setVisibility(View.VISIBLE);
                binding.condition.setText("hotter");
                dryWeather();
            }
            if (temperature <= 20 && temperature > 14) {
                binding.blueSky.setVisibility(View.VISIBLE);
                binding.condition.setText("light \nsunny");
                dryWeather();
            } else {
                if (temperature >= 12 && temperature <= 14) {
                    setNoHotWeather();
                    binding.condition.setText("cloudy");
                    rainy_monitoring();
                } else {
                    if (temperature >= 7 && temperature < 12) {
                        setNoHotWeather();
                        binding.condition.setText("cold");
                        rainy_monitoring();

                    } else {
                        if (temperature < 7) {
                            setNoHotWeather();
                            binding.condition.setText("very \ncold");
//                            snow_monitoring();
                        }
                    }
                }
            }
        }

        public void rainy_monitoring(){
            if(humidity_c <=55){
                binding.rainLotty.setVisibility(View.INVISIBLE);
                binding.isRainyOrNot.setText("");
                dryWeather();
            }else {
                rainy_possible();
            }
        }



        public void setNoHotWeather() {
            binding.blueSky.setVisibility(View.INVISIBLE);
            binding.sun.setVisibility(View.INVISIBLE);
            binding.badWeatherSky.setVisibility(View.VISIBLE);

        }


        public String getCurrDateTime(long a) {
            String new_a = java.text.DateFormat.getDateTimeInstance().format(new Date(a));
            return new_a;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
