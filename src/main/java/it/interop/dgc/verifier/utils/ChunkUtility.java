package it.interop.dgc.verifier.utils;

import it.interop.dgc.verifier.exceptions.BusinessException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author vincenzoingenito
 *
 */
@Slf4j
public final class ChunkUtility {

    private ChunkUtility() {}

    /**
     *
     * @param source   full list
     * @param lenght   max chunk
     * @return         Stream<List<T>>
     */
    public static <T> Stream<List<T>> calcolaChunk(List<T> source, int length) {
        if (length <= 0) {
            throw new BusinessException("get lenght > 0 ");
        }

        int size = source.size();
        if (size <= 0) {
            return Stream.empty();
        }

        int fullChunks = (size - 1) / length;
        return IntStream
            .range(0, fullChunks + 1)
            .mapToObj(n ->
                source.subList(
                    n * length,
                    n == fullChunks ? size : (n + 1) * length
                )
            );
    }

    /**
     *
     * @param source   full list
     * @return         long
     */
    public static long getBytesFromObj(Object list) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(list);
            out.close();
        } catch (Exception ex) {
            log.error("Error in calculating the size in bytes:", ex);
            throw new BusinessException(
                "Error in calculating the size in bytes:" + ex
            );
        }
        return baos.toByteArray().length;
    }
}
