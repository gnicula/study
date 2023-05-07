package com.mycompany.a4;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {

	private Media sndMedia;

	public BGSound(String fIn) {
		while (sndMedia == null) { // ADD THIS
			try {
				InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fIn);
				sndMedia = MediaManager.createMedia(in, "audio/wav", this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void pause() {
		sndMedia.pause();
	}

	public void play() {
		sndMedia.play();
	}

	// Should loop from beginning.
	@Override
	public void run() {
		sndMedia.setTime(0);
		sndMedia.play();
	}

}