package by.gmar.exceptions;

/**
 *
 * @author s.kosik
 */
public enum ErrorCodes {
    Unknown(-1), UnknownCategory(0), UserChallengeIsAbsent(1), ChallengeUsersIsNotMatch(2),
    ChallengeBusinessValueError(3), UserVitalInfoAbsence(4), PasswordValidation(5), 
    DriveFileNotFound(6), IncopatibleDriveFileType(7), UserPersonalDataConstraint(8),
    FeatureUsageLimitation(9), ThirdPartyServiceError(10),
    ImportInProgress(11), UserLoginException(12), RegistrationIException(13),
    UnsupportIncomeData(14), ActualDataAbsent(15), EmailExist(19),
    AccessDenied(16), TooBigFile(17), PaymentException(18);
    
    private int value;

    ErrorCodes(final int caloriesPerMinute) {
        value = caloriesPerMinute;
    }

    public int getValue() {
        return value;
    }
}
