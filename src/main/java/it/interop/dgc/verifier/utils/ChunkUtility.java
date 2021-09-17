package it.interop.dgc.verifier.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.interop.dgc.verifier.exceptions.BusinessException;


/**
 * 
 * @author vincenzoingenito
 *
 */
public final class ChunkUtility { 

    private ChunkUtility() {
    }

    /**
     * 
     * @param source   lista completa
     * @param lenght   max chunk
     * @return         Stream<List<T>>
     */
    public static <T> Stream<List<T>> calcolaChunk(List<T> source, int length) {

        if (length <= 0) {
            throw new BusinessException("Fornire una lunghezza > 0 ");
        }

        int size = source.size();
        if (size <= 0) {
            return Stream.empty();
        }

        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }
    
    /**
     * 
     * @param source   lista completa 
     * @return         long
     */
    public static long getBytesFromObj(Object list) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {           
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(list);
            out.close();
        } catch(Exception ex) {
            throw new BusinessException("Errore nel calcolo della size in byte : " + ex);
        } 
        return baos.toByteArray().length;
    }
    
   

}