package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {

	private Media mmanager;

	public BGSound(String fIn) {
		try {
			InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fIn);
			mmanager = MediaManager.createMedia(in, "audio/wav", this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		mmanager.pause();
	}

	public void play() {
		mmanager.play();
	}

	@Override
	public void run() {
		mmanager.setTime(0);
		mmanager.play();
	}

}