package com.example.bb.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.bb.database.relations.PatientAndCase

@Dao
interface BBDatabaseDao {
    @Insert
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentUser(currentUser: CurrentUser)

    @Insert
    fun insertCase(case: Case)

    @Insert
    fun insertCharity(charity: Charity)

    @Insert
    fun insertDonation(donation: Donation)

    @Update
    fun update(user: User)

    @Update
    fun updateCase(case: Case)

    @Update
    fun updateCharity(charity: Charity)

    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password")
    fun find(email: String, password: String): User?

    @Query("SELECT * FROM users_table ORDER BY id DESC LIMIT 1")
    fun getLastUser(): User?

    @Query("SELECT * FROM charities_table")
    fun getCharities(): LiveData<List<Charity>>

    @Query("SELECT * FROM cases_table")
    fun getCases(): LiveData<List<Case>>

    @Query("SELECT * FROM charities_table WHERE id = :charityId")
    fun getCharityById(charityId: Long): Charity?

    @Query("SELECT * FROM cases_table WHERE user_id = :userId")
    fun getCaseByUserId(userId: Long): Case

    @Query("SELECT * FROM cases_table WHERE id = :caseId")
    fun getCaseByCaseId(caseId: Long): Case

    @Query("SELECT * FROM cases_table WHERE charity_id = :charityId")
    fun getCharityCases(charityId: Long): List<Case>?

    @Query("SELECT * FROM cases_table ORDER BY id DESC LIMIT 1")
    fun getLastCase(): Case?

    @Query("SELECT * FROM current_user")
    fun getCurrentUser():Long

    //TEST
    @Transaction
    @Query("SELECT * FROM users_table WHERE id = :userId")
    fun getPatientAndCaseWithUserId(userId: Long): List<PatientAndCase>

//    @Transaction
//    @Query("SELECT * FROM users_table WHERE id = :userId")
//    fun getPatientAndCaseWithUserId(userId: Long): List<PatientAndCase>

//    @Transaction
//    @Query("SELECT * FROM charities_table WHERE id = :charityId")
//    fun getCharityWithCases(charityId: Long): List<CharityWithCases>
//
//    @Transaction
//    @Query("SELECT * FROM donations_table WHERE user_id = :userId")
//    fun getUserWithDonations(userId: Long): List<DonorWithDonations>
//
//    @Transaction
//    @Query("SELECT * FROM donations_table WHERE case_num = :caseNum")
//    fun getCaseWithDonations(caseNum: Long): List<CaseWithDonations>
//
//    @Transaction
//    @Query("SELECT * FROM donations_table WHERE charity_name = :charityName")
//    fun getCharityWithDonations(charityName: String): List<CharityWithDonations>
    @Query("DELETE FROM current_user")
    fun clearCurrentUser()

    @Query("DELETE FROM charities_table")
    fun clearCharities()

    @Query("DELETE FROM users_table")
    fun clear()
}