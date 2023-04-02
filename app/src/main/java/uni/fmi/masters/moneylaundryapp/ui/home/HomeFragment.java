package uni.fmi.masters.moneylaundryapp.ui.home;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uni.fmi.masters.moneylaundryapp.databinding.FragmentHomeBinding;
import uni.fmi.masters.moneylaundryapp.game.GameView;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        GameView gameView = new GameView(getActivity(), point.x, point.y);

        return gameView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}