package com.example.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.example.R;

import java.sql.SQLException;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 * 
 * @author kevingalligan
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "click.db";
	private static final int DATABASE_VERSION = 7;

	private Dao<Student, Integer> studentDao;
	private Dao<Group, Integer> groupDao;
	private Dao<Sport, Integer> sportDao;
	private Dao<StudentSport, Integer> studentSportDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}


	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Student.class);
			TableUtils.createTable(connectionSource, Group.class);
			TableUtils.createTable(connectionSource, Sport.class);
			TableUtils.createTable(connectionSource, StudentSport.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, Student.class, true);
			TableUtils.dropTable(connectionSource, Group.class, true);
			TableUtils.dropTable(connectionSource, Sport.class, true);
			TableUtils.dropTable(connectionSource, StudentSport.class, true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
		}
	}

	public Dao<Student, Integer> getStudentDao() throws SQLException {
		if (studentDao == null) {
            studentDao = getDao(Student.class);
		}
		return studentDao;
	}

	public Dao<Group, Integer> getGroupDao() throws SQLException {
		if (groupDao == null) {
            groupDao = getDao(Group.class);
		}
		return groupDao;
	}

	public Dao<Sport, Integer> getSportDao() throws SQLException {
		if (sportDao == null) {
            sportDao = getDao(Sport.class);
		}
		return sportDao;
	}

	public Dao<StudentSport, Integer> getStudentSportDao() throws SQLException {
		if (studentSportDao == null) {
            studentSportDao = getDao(StudentSport.class);
		}
		return studentSportDao;
	}
}
