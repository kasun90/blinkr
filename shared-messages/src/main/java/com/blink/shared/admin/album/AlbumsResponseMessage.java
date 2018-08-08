package com.blink.shared.admin.album;

import java.util.List;
import com.blink.utilities.BlinkJSON;
import com.blink.shared.common.Album;

public class AlbumsResponseMessage {
	private List<Album> albums;
	private int total;

	public AlbumsResponseMessage() {}

	public AlbumsResponseMessage(List<Album> albums, int total) {
		this.albums = albums;
		this.total = total;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public AlbumsResponseMessage setAlbums(List<Album> albums) {
		this.albums = albums;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public AlbumsResponseMessage setTotal(int total) {
		this.total = total;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}