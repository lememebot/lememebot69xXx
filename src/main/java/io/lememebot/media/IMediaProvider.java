package io.lememebot.media;

import javax.print.attribute.standard.Media;
import java.util.List;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.media
 * Created by Guy on 12-Mar-17.
 * <p>
 * Description:
 */
public interface IMediaProvider {

    List<MediaDescriptor> getMediaDescriptorList();
}
