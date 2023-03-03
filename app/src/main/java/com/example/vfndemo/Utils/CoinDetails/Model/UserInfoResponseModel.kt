package com.example.vfndemo.Utils.CoinDetails.Model

import androidx.annotation.Keep

@Keep
class UserInfoResponseModel(
    val message: String? = null,
    val data: UserInfoModel? = null,
)

@Keep
class UserInfoModel(
    var userId: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var userName: String? = null,
    var ageGroup: String? = null,
    var isCryptoInvestor: Boolean? = null,
    var bio: String? = null,
    var setupDone: Boolean? = false,
    val imageUrl: String? = null,
    val smallImgUrl: String? = null,
    var phoneNumber: String? = null,
    var fullName: String? = null,
    var friends: Int = 0,
    var followers: Int = 0,
    var followings: Int = 0,
    var earlyAccessAllowed: Boolean,
    var showWhatsappShareSheet: Boolean,
    var showSkipInvest: Boolean,
    var profileType: String? = null,
    var earnedPoints: Boolean? = null,
    var profilePoints: Double? = null,
    var badgeDetails: BadgeDetails? = null,
    var refCode: String? = null,
    var refLink: String? = null,
    var metadata: MetaData? = null,
    val subscriptionType: String?,
)

@Keep
data class MetaData(
    val ageGroup: String?,
    val isCryptoInvestor: Boolean?,
    val profileBadge: String?,
//    val socialHandlers: SocialHandlers?,
    val subscriptionType: String?,
    val tags: List<String>?,
)

@Keep
data class BadgeDetails(
    var smallBadgeUrl: String? = null,
    var largeBadgeUrl: String? = null,
    var badge: String? = null,
    var externalUrl: String? = null,
    var badgeDetails: String? = null,
)

