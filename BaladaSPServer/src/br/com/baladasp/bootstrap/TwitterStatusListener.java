package br.com.baladasp.bootstrap;

import java.util.Calendar;

import twitter4j.FilterQuery;
import twitter4j.MediaEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.baladasp.cdp.usuario.StatusUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.util.Utils;

public class TwitterStatusListener {

	//TODO arrumar essa bagunça
	public void listenerStatus() {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		String[] busca = new String[] { "#appbaladasp" };
		FilterQuery fq = new FilterQuery();
		fq.track(busca);

		StatusListener listener = new StatusListener() {
			@Override
			public void onStatus(Status status) {
				StatusUsuariosBO statusBO = new StatusUsuariosBO();

				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText() + " - "
						+ status.getCreatedAt());

				long idTwitter = status.getUser().getId();

				String date = Utils.formatarData(status.getCreatedAt());

				String[] split = status.getText().split("http://");
				String nomeEstabelecimento = split[0];

				String linkImagem = "";
				String mediaURL = "";

				for (MediaEntity media : status.getMediaEntities()) {
					mediaURL = media.getMediaURL();
					linkImagem = media.getURL();
					System.out.println(mediaURL);
				}

				Usuario user = new Usuario(status.getUser().getName(), idTwitter, "@"
						+ status.getUser().getScreenName(), status.getUser().getProfileImageURL());

				UsuarioBO usuarioBO = new UsuarioBO();
				Usuario usuario = usuarioBO.consultarUsuarioIDTwitter(idTwitter);

				if (usuario == null) {
					usuarioBO.inserirUsuario(user);
					usuario = usuarioBO.consultarUsuarioIDTwitter(idTwitter);
				}

				StatusUsuario statusUsuario = new StatusUsuario(date, nomeEstabelecimento, linkImagem, mediaURL,
						usuario);

				statusBO.inserirStatus(statusUsuario);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}

// public class GeoTweets {
// public static void main(String[] args) {
// TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
//
// StatusListener listener = new StatusListener() {
//
// @Override
// public void onException(Exception arg0) {
// // TODO Auto-generated method stub
// }
//
// @Override
// public void onDeletionNotice(StatusDeletionNotice arg0) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onScrubGeo(long arg0, long arg1) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onStatus(Status status) {
// User user = status.getUser();
//
// String username = status.getUser().getScreenName();
// String profileLocation = user.getLocation();
// long tweetId = status.getId();
// String content = status.getText();

// GeoLocation geolocation = status.getGeoLocation();
// double tweetLatitude = geolocation.getLatitude();
// double tweetLongitude = geolocation.getLongitude();
//
// String application = status.getSource();
// System.out.println(application);
//
// Place placenameJSON = status.getPlace();
// System.out.println(placenameJSON);
// String placename = placenameJSON.getFullName();
// System.out.println(placename);
// Date time = status.getCreatedAt();
// System.out.println(time);
//
// System.out.println(tweetLatitude + "," + tweetLongitude + "\n");
// }
//
// @Override
// public void onTrackLimitationNotice(int arg0) {
// // TODO Auto-generated method stub
// System.out.println("onTrackLimitationNotice" + "\n");
//
// }
//
// @Override
// public void onStallWarning(StallWarning arg0) {
// // TODO Auto-generated method stub
// System.out.println("onStallWarning" + "\n");
//
// }
//
// };
// FilterQuery fq = new FilterQuery();
// double lat = 53.270141;
// double longitude = -9.055488;
// double lat1 = lat - .25;
// double longitude1 = longitude - .25;
// double lat2 = lat + .25;
// double longitude2 = longitude + .25;
// twitterStream.addListener(listener);
// double[][] bb = { { longitude1, lat1 }, { longitude2, lat2 } };
//
// fq.locations(bb);
//
// twitterStream.filter(fq);
//
// }
// }
