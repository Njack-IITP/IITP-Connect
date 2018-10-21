package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.iitp.njack.iitp_connect.data.user.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileRepositoryTest {

    private static final String MOCK_USER_UID = "test_uid";
    private static final User MOCK_USER = new User();

    private ProfileRepository profileRepository;

    @Mock
    private DatabaseReference databaseReference;

    @Mock
    private FirebaseAuth firebaseAuth;

    @Before
    public void setUp() {
        FirebaseUser mockFirebaseUser = Mockito.mock(FirebaseUser.class);

        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        Mockito.when(mockFirebaseUser.getUid()).thenReturn(MOCK_USER_UID);

        DatabaseReference mainDatabaseReference = Mockito.mock(DatabaseReference.class);

        Mockito.when(mainDatabaseReference.child("users")).thenReturn(mainDatabaseReference);
        Mockito.when(mainDatabaseReference.child("users").child(MOCK_USER_UID)).thenReturn(databaseReference);
        profileRepository = new ProfileRepository(mainDatabaseReference, firebaseAuth);
    }

    @Test
    public void shouldSetUser() {
        // when
        profileRepository.setUser(MOCK_USER);

        // then
        Mockito.verify(databaseReference).setValue(MOCK_USER);
    }


    @Test
    public void shouldSetField() {
        // given
        Mockito.when(databaseReference.child(UserFieldType.USERNAME.key))
            .thenReturn(databaseReference);

        // when
        profileRepository.setField(UserFieldType.USERNAME, "test_username");

        // then
        Mockito.verify(databaseReference).setValue("test_username");
    }

    @Test
    public void shouldReturnUserLiveData() {
        // when
        LiveData<DataSnapshot> userLiveData = profileRepository.getUser();

        // then
        Assert.assertSame(userLiveData, profileRepository.firebaseDatabaseLiveData);
    }
}
