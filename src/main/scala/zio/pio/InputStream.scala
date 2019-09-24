package zio.pio

import java.io.{ InputStream => JInputStream }
import zio._
import java.io.IOException
import zio.stream._

/** This could probably be used somehow ..
 *  19 import java.io.IOException;                                                                                                                                                                                                              
 20 import java.io.RandomAccessFile;                                                                                                                                                                                                         
 21 import java.nio.ByteBuffer;                                                                                                                                                                                                              
 22 import java.nio.channels.FileChannel;                                                                                                                                                                                                    
 23                                                                                                                                                                                                                                          
 24 class FileIterator(file: String, bufferSize: Int) extends scala.Iterator[Array[Byte]] {                                                                                                                                                  
 25                                                                                                                                                                                                                                          
 26   val aFile = new RandomAccessFile(file, "r");                                                                                                                                                                                           
 27   val inChannel = aFile.getChannel()                                                                                                                                                                                                     
 28   val buffer = ByteBuffer.allocate(bufferSize);                                                                                                                                                                                          
 29   var array: Array[Byte] = Array.fill(bufferSize)(0x0)                                                                                                                                                                                   
 30                                                                                                                                                                                                                                          
 31   def hasNext: Boolean = inChannel.read(buffer) > 0                                                                                                                                                                                      
 32                                                                                                                                                                                                                                          
 33   def next(): Array[Byte] = {                                                                                                                                                                                                            
 34                                                                                                                                                                                                                                          
 35     Array.fill(bufferSize)(0x0)                                                                                                                                                                                                          
 36                                                                                                                                                                                                                                          
 37     buffer.flip();                                                                                                                                                                                                                       
 38     var i = 0                                                                                                                                                                                                                            
 39     while (i < buffer.limit() ){                                                                                                                                                                                                         
 40       array(i) = buffer.get()                                                                                                                                                                                                            
 41       i += 1                                                                                                                                                                                                                             
 42     }                                                                                                                                                                                                                                    
 43                                                                                                                                                                                                                                          
 44     buffer.clear(); // do something with the data and clear/compact it.                                                                                                                                                                  
 45                                                                                                                                                                                                                                          
 46     array                                                                                                                                                                                                                                
 47                                                                                                                                                                                                                                          
 48   }                                                                                                                                                                                                                                      
 49                                                                                                                                                                                                                                          
 50   def close(): Unit = {                                                                                                                                                                                                                  
 51     buffer.clear()                                                                                                                                                                                                                       
 52     inChannel.close()                                                                                                                                                                                                                    
 53     aFile.close()                                                                                                                                                                                                                        
 54   }                                                                                                                                                                                                                                      
 55                                                                                                                                                                                                                                          
 56 } */

 // put the jInputStream into a TRef?
class InputStream private[pio] (private[pio] val jInputStream: JInputStream) {

  def readFromStdOut(bufferSize: Int): ZStream[Any, IOException, Array[Byte]] = {

    ZStream.fromIterable { new Iterable[Array[Byte]] {

      override def iterator = new Iterator[Array[Byte]] {
        override def next(): Array[Byte] = {
          // compiler complaints and says it wants this to be a val
          val arrayBuf: Array[Byte] = Array.fill(bufferSize)(0x0)
          val nrOfBytesRead = jInputStream.read(arrayBuf)
          if(nrOfBytesRead == -1) {
            jInputStream.close()
          }
          arrayBuf

          // for(i <- 0 to arrayBuf.length) {
          //   arrayBuf(i) = 0x0
          // }
          
          // arrayBuf
        }
        override def hasNext: Boolean = jInputStream.read() != -1
      }
      
    }

    }
    
  }
}

object InputStream {

  def apply(jInputStream: JInputStream) =
    IO.effect(new InputStream(jInputStream))

}
