package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {

	private Media media;

	public Sound(String fIn) {
		try {
			InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fIn);
			media = MediaManager.createMedia(in, "audio/wav");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
//		media.setTime(0);
		media.play();
	}

}
