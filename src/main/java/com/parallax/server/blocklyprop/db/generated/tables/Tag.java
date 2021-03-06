/**
 * This class is generated by jOOQ
 */
package com.parallax.server.blocklyprop.db.generated.tables;


import com.parallax.server.blocklyprop.db.generated.Blocklyprop;
import com.parallax.server.blocklyprop.db.generated.Keys;
import com.parallax.server.blocklyprop.db.generated.tables.records.TagRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tag extends TableImpl<TagRecord> {

	private static final long serialVersionUID = 851708876;

	/**
	 * The reference instance of <code>blocklyprop.tag</code>
	 */
	public static final Tag TAG = new Tag();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TagRecord> getRecordType() {
		return TagRecord.class;
	}

	/**
	 * The column <code>blocklyprop.tag.id</code>.
	 */
	public final TableField<TagRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>blocklyprop.tag.name</code>.
	 */
	public final TableField<TagRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * Create a <code>blocklyprop.tag</code> table reference
	 */
	public Tag() {
		this("tag", null);
	}

	/**
	 * Create an aliased <code>blocklyprop.tag</code> table reference
	 */
	public Tag(String alias) {
		this(alias, TAG);
	}

	private Tag(String alias, Table<TagRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tag(String alias, Table<TagRecord> aliased, Field<?>[] parameters) {
		super(alias, Blocklyprop.BLOCKLYPROP, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TagRecord, Long> getIdentity() {
		return Keys.IDENTITY_TAG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TagRecord> getPrimaryKey() {
		return Keys.KEY_TAG_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TagRecord>> getKeys() {
		return Arrays.<UniqueKey<TagRecord>>asList(Keys.KEY_TAG_PRIMARY, Keys.KEY_TAG_NAME_UNIQUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag as(String alias) {
		return new Tag(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Tag rename(String name) {
		return new Tag(name, null);
	}
}
