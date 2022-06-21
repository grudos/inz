package com.example.testing

import com.amazonaws.services.chime.sdk.meetings.session.Attendee
import com.amazonaws.services.chime.sdk.meetings.session.Meeting

// Data stucture that maps to the HTTP response.
data class JoinMeetingResponse(
    @SerializedName("JoinInfo")
    val joinInfo: MeetingInfo)

annotation class SerializedName(val value: String)

data class MeetingInfo(
    @SerializedName("Meeting")
    val meetingResponse: MeetingResponse,
    @SerializedName("Attendee")
    val attendeeResponse: AttendeeResponse)

data class MeetingResponse(
    @SerializedName("Meeting")
    val meeting: Meeting
)

data class AttendeeResponse(
    @SerializedName("Attendee")
    val attendee: Attendee
)
