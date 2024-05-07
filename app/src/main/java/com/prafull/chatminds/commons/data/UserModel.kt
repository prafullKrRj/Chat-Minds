package com.prafull.chatminds.commons.data

import com.prafull.chatminds.features.history.domain.HistoryModel

data class UserModel(
    val name: String,           // User's name
    val subscribed: Boolean,    // Subscription status
    val history: HistoryModel,  // User's history
    val currCoins: Long,        // Current coins
    val subscriptionType: SubscriptionType
)
sealed interface SubscriptionType {
    data object Free : SubscriptionType
    data class PremiumWeekly(val expiryDate: Long) : SubscriptionType
    data class PremiumMonthly(val expiryDate: Long) : SubscriptionType
    data class PremiumYearly(val expiryDate: Long) : SubscriptionType
}