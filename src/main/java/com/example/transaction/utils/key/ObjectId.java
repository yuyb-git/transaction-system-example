package com.example.transaction.utils.key;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 程序描述: 分布式环境下的全局唯一标识符对象。
 *
 * @author baihw
 */

public class ObjectId {
	private static final int CHAR_LEN = 32;
	private static final int BYTE_LEN = 16;

	private final int time;
	private final int machine;
	private final int inc;
	private final int shard;
	private final int shardSize;
	private final boolean isNew;

	/**
	 * 创建一个默认的全局唯一标识
	 */
	public ObjectId() {
		time = (int) (System.currentTimeMillis() / 1000);
		machine = GEN_MACHINE;
		inc = NEXT_INC.getAndIncrement();
		shard = 0;
		shardSize = 0;
		isNew = true;
	}

	/**
	 * 根据指定的分区及分区大小信息创建一个唯一标识
	 *
	 * @param shard     分区标识,取值范围:0-32767
	 * @param shardSize 分区下的存储区域大小标识,取值范围:0-65535
	 */
	public ObjectId(int shard, int shardSize) {
		if (0 > shard || 32767 < shard) {
			throw new RuntimeException("分区大小范围在0-32767。");

		}
		if (0 > shardSize || 65535 < shardSize) {
			throw new RuntimeException("子分区大小范围在0-65535。");
		}
		time = (int) (System.currentTimeMillis() / 1000);
		machine = GEN_MACHINE;
		inc = NEXT_INC.getAndIncrement();
		this.shard = shard;
		this.shardSize = shardSize;
		isNew = true;
	}

	/**
	 * 从一个已有的唯一标识字符串中解析出唯一标识对象
	 *
	 * @param objectIdStr
	 */
	public ObjectId(String objectIdStr) {
		if (!isValid(objectIdStr)) {
			throw new RuntimeException("无效的标识字符串，长度必须是32位，只能包含0-9,a-f,A-F字符！");
		}

		byte b[] = new byte[BYTE_LEN];
		for (int i = 0; i < BYTE_LEN; i++) {
			b[i] = (byte) Integer.parseInt(objectIdStr.substring(i * 2, (i * 2) + 2), 16);
		}

		ByteBuffer bb = ByteBuffer.wrap(b);
		time = bb.getInt();
		machine = bb.getInt();
		inc = bb.getInt();
		final int tempShardSize = bb.getInt();
		shard = (tempShardSize >> 16);
		shardSize = (tempShardSize ^ (shard << 16));
		isNew = false;
	}

	/**
	 * 检查指定的字符串是否为有效的唯一标识。
	 *
	 * @param s
	 * @return
	 */
	public static boolean isValid(String s) {
		if (null == s) {
			return false;

		}

		final int len = s.length();
		if (CHAR_LEN != len) {
			return false;

		}

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				continue;

			}
			if (c >= 'a' && c <= 'f') {
				continue;

			}
			if (c >= 'A' && c <= 'F') {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 将当前标识符对象元素整合到二进行数组中。
	 *
	 * @return
	 */
	public byte[] toByteArray() {
		byte b[] = new byte[BYTE_LEN];
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.putInt(time);
		bb.putInt(machine);
		bb.putInt(inc);
		bb.putInt(((shard << 16) | shardSize));
		return b;
	}

	/**
	 * 获取分区信息
	 *
	 * @return
	 */
	public int getShard() {
		return shard;
	}

	/**
	 * 获取分区大小信息
	 *
	 * @return
	 */
	public int getShardSize() {
		return shardSize;
	}

	/**
	 * 获取当前唯一标识的生成日期
	 *
	 * @return
	 */
	public Date getDate() {
		return new Date(time * 1000L);
	}

	/**
	 * 获取当前唯一标识的生成时间戳
	 *
	 * @return
	 */
	public long getTimestamp() {
		return time * 1000L;
	}

	public int getMachine() {
		return machine;
	}

	public int getInc() {
		return inc;
	}

	/**
	 * 当前的唯一标识符对象是否为新生成的。
	 *
	 * @return
	 */
	public boolean isNew() {
		return isNew;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + inc;
		result = prime * result + machine;
		result = prime * result + shard;
		result = prime * result + shardSize;
		result = prime * result + time;
		return result;
	}

	@Override
	public boolean equals(Object objId) {
		if (this == objId) {
			return true;

		}
		if (null == objId || getClass() != objId.getClass()) {
			return false;
		}

		ObjectId other = (ObjectId) objId;
		return time == other.time && machine == other.machine && inc == other.inc && shard == other.shard && shardSize == other.shardSize;
	}

	@Override
	public String toString() {
		byte[] b = toByteArray();
		StringBuilder buf = new StringBuilder(32);
		for (int i = 0; i < b.length; i++) {
			int x = b[i] & 0xFF;
			String s = Integer.toHexString(x);
			if (s.length() == 1) {
				buf.append("0");
			}
			buf.append(s);
		}
		return buf.toString();
	}

	protected String toHexString() {
		byte[] bs = toByteArray();
		final StringBuilder buf = new StringBuilder(32);
		for (final byte b : bs) {
			buf.append(String.format("%02x", b & 0xff));
		}

		return buf.toString();
	}

	private static final AtomicInteger NEXT_INC = new AtomicInteger((new Random()).nextInt());
	private static final int GEN_MACHINE;

	static {
		try {
			// build a 2-byte machine piece based on NICs info
			int machinePiece;
			{
				try {
					StringBuilder sb = new StringBuilder();
					Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface ni = e.nextElement();
						sb.append(ni.toString());
					}
					machinePiece = sb.toString().hashCode() << 16;
				} catch (Throwable e) {
					// exception sometimes happens with IBM JVM, use random
					// LOGGER.log(Level.WARNING, e.getMessage(), e);
					machinePiece = (new Random().nextInt()) << 16;
				}
				// LOGGER.fine( "machine piece post: " + Integer.toHexString( machinePiece ) );
			}

			// add a 2 byte process piece. It must represent not only the JVM
			// but the class loader.
			// Since static var belong to class loader there could be collisions
			// otherwise
			final int processPiece;
			{
				int processId = new Random().nextInt();
				try {
					processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
				} catch (Throwable t) {
				}

				ClassLoader loader = ObjectId.class.getClassLoader();
				int loaderId = loader != null ? System.identityHashCode(loader) : 0;

				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toHexString(processId));
				sb.append(Integer.toHexString(loaderId));
				processPiece = sb.toString().hashCode() & 0xFFFF;
				// LOGGER.fine( "process piece: " + Integer.toHexString( processPiece ) );
			}

			GEN_MACHINE = machinePiece | processPiece;
			// LOGGER.fine( "machine : " + Integer.toHexString( _genmachine ) );
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转换为简单类型，不包含分区分片信息，长度为24位。
	 *
	 * @return
	 */
	public String toSimple() {
		return toString().substring(0, 24);
	}

	public static void main(String[] args) {
		System.out.println(new ObjectId().toSimple());
	}
} // End class
