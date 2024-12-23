package com.mycompany.a4;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

// New in A3, Sound class that loads a file and plays it.
public class Sound {

	private Media sndMedia = null;

	public Sound(String fIn) {
		while (sndMedia == null) { // ADD THIS
			try {
				InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fIn);
				sndMedia = MediaManager.createMedia(in, "audio/wav");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Media getMedia() {
		return sndMedia;
	}

	public void play() {
		sndMedia.setTime(0);
		sndMedia.play();
	}

}
