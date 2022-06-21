package com.example.testing

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amazonaws.services.chime.sdk.meetings.audiovideo.AttendeeInfo
import com.amazonaws.services.chime.sdk.meetings.audiovideo.AudioVideoObserver
import com.amazonaws.services.chime.sdk.meetings.audiovideo.SignalUpdate
import com.amazonaws.services.chime.sdk.meetings.audiovideo.VolumeUpdate
import com.amazonaws.services.chime.sdk.meetings.audiovideo.metric.MetricsObserver
import com.amazonaws.services.chime.sdk.meetings.audiovideo.metric.ObservableMetric
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.RemoteVideoSource
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileObserver
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileState
import com.amazonaws.services.chime.sdk.meetings.realtime.RealtimeObserver
import com.amazonaws.services.chime.sdk.meetings.session.*
import com.amazonaws.services.chime.sdk.meetings.utils.logger.ConsoleLogger
import com.amazonaws.services.chime.sdk.meetings.utils.logger.LogLevel
import com.amazonaws.services.chime.sdk.meetings.utils.logger.Logger
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.nio.file.Paths


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StreamVideoAudioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StreamVideoAudioFragment : Fragment(), AudioVideoObserver, RealtimeObserver,
    VideoTileObserver, MetricsObserver {

    val logger = ConsoleLogger(LogLevel.VERBOSE)
    val audioVideo = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //StreamVideoAudioFragment.appContext = applicationContext

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stream_video_audio, container, false)


        val attendee = "attendee"
        val meetingId = "ab"
        val meetingUrl = "https://evj4xcv612.execute-api.us-east-1.amazonaws.com/Prod/"


        val attendeeName = java.net.URLEncoder.encode(attendee, "utf-8");
        val region = java.net.URLEncoder.encode("us-east-1", "utf-8");
        val title = java.net.URLEncoder.encode(meetingId, "utf-8");
        val url = "${meetingUrl}join?title=$title&name=$attendeeName&region=$region";

        //val response: String = File("response.json").readText(Charsets.UTF_8)

        //val path = context?.getFilesDir()
        //val path = Paths.get("").toAbsolutePath().toString()

        val context: Context = view.getContext()

        //val fileName = "D:\\INFA\\testing\\app\\src\\main\\java\\com\\example\\testing\\response.json"


        //val fileName = "com\\example\\testing\\response.json"
        //val fileName = "response.json"
        //val fileName = "src/main/java/com/example/testing/response.json"
        //val fileName = "app\\src\\main\\java\\com\\example\\testing\\response.json"
        //val inputStream: InputStream = File(fileName).inputStream()

        //val path = System.getProperty("user.dir")
        //val path2 = Paths.get("").toAbsolutePath().toString()


        //val response = FileReader(fileName)


        //val response = """[{"name":"John","type":"Technical Author"},{"name":"Jane","type":"Technical Author"},{"name":"William","type":"Technical Editor"}]"""
        //val response = getJson()
        /*
        val file = File(filePath)

        val bool = file.exists()

        if (isFileExists(file)) {
            println("File exists!!")
        } else {
            println("File doesn't exist or program doesn't have access to it")
        }
*/
        //val response = getJsonDataFromAsset(context, fileName)

        //val response = inputStream.bufferedReader().use { it.readText() }

        val response = loadJson(context)

        //println(inputString)


        val joinMeetingResponse = Gson().fromJson(
            response.toString(),
            JoinMeetingResponse::class.java
        )

        // Construct configuration using the meeting response.
        val configuration = MeetingSessionConfiguration(
            CreateMeetingResponse(joinMeetingResponse.joinInfo.meetingResponse.meeting),
            CreateAttendeeResponse(joinMeetingResponse.joinInfo.attendeeResponse.attendee)
        )

        // Create a default meeting seesion.


        val meetingSession = DefaultMeetingSession(configuration, ConsoleLogger(), context)
        val audioVideo = meetingSession.audioVideo


        // Start audio and video clients.
        audioVideo.start()

        // Mute local audio input.
        //audioVideo.realtimeLocalMute()

        // Unmute local audio input.
        //audioVideo.realtimeLocalUnmute()

        // Start receiving remote video.
        audioVideo.startRemoteVideo()

        // Start sending local video.
        audioVideo.startLocalVideo()

        // Switch camera for local video between front and back.
        //audioVideo.switchCamera()


        // Register the observer.
        audioVideo.addAudioVideoObserver(this)

        // Register the observer.
        audioVideo.addRealtimeObserver(this)

        //Once you get a list of attendees, you can fetch the attendee name from the externalUserId by the following code.
        //val attendeeNameReceive = attendeeInfo.externalUserId.split('#')[1]

        // Register the observer.
        audioVideo.addVideoTileObserver(this)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StreamVideoAudioFragment.
         */

        lateinit var appContext: Context

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StreamVideoAudioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onAudioSessionCancelledReconnect() {
        TODO("Not yet implemented")
    }

    override fun onAudioSessionDropped() {
        TODO("Not yet implemented")
    }

    override fun onAudioSessionStarted(reconnecting: Boolean) =
        logger.info(TAG, "Audio successfully started. reconnecting: $reconnecting")

    override fun onAudioSessionStartedConnecting(reconnecting: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAudioSessionStopped(sessionStatus: MeetingSessionStatus) =
        logger.info(TAG, "Audio stopped for reason: ${sessionStatus.statusCode}")

    override fun onConnectionBecamePoor() {
        TODO("Not yet implemented")
    }

    override fun onConnectionRecovered() {
        TODO("Not yet implemented")
    }

    override fun onRemoteVideoSourceAvailable(sources: List<RemoteVideoSource>) {
        TODO("Not yet implemented")
    }

    override fun onRemoteVideoSourceUnavailable(sources: List<RemoteVideoSource>) {
        TODO("Not yet implemented")
    }

    override fun onVideoSessionStarted(sessionStatus: MeetingSessionStatus) {
        logger.info(TAG, "Video successfully started: ${sessionStatus.statusCode}")
    }

    override fun onVideoSessionStartedConnecting() {
        TODO("Not yet implemented")
    }

    override fun onVideoSessionStopped(sessionStatus: MeetingSessionStatus) =
        logger.info(TAG, "Video stopped for reason: ${sessionStatus.statusCode}")

    override fun onAttendeesDropped(attendeeInfo: Array<AttendeeInfo>) {
        TODO("Not yet implemented")
    }

    // Notifies when attendees joined the meeting.
    override fun onAttendeesJoined(attendeeInfo: Array<AttendeeInfo>) {
        attendeeInfo.forEach {
            logger.debug(
                TAG,
                "Attendee join. attendee Id: ${it.attendeeId} external user Id: ${it.externalUserId}"
            )
        }
    }

    override fun onAttendeesLeft(attendeeInfo: Array<AttendeeInfo>) {
        TODO("Not yet implemented")
    }

    override fun onAttendeesMuted(attendeeInfo: Array<AttendeeInfo>) {
        TODO("Not yet implemented")
    }

    override fun onAttendeesUnmuted(attendeeInfo: Array<AttendeeInfo>) {
        TODO("Not yet implemented")
    }

    override fun onSignalStrengthChanged(signalUpdates: Array<SignalUpdate>) {
        TODO("Not yet implemented")
    }

    // Notifies when volume levels changed.
    override fun onVolumeChanged(volumeUpdates: Array<VolumeUpdate>) {
        volumeUpdates.forEach { (attendeeInfo, volumeLevel) ->
            logger.info(
                TAG,
                "AttendeeId: ${attendeeInfo.attendeeId} externalUserId: ${attendeeInfo.externalUserId} volumeLevel: $volumeLevel"
            )
        }
    }

    override fun onVideoTileAdded(tileState: VideoTileState) {
        logger.info(
            TAG,
            "Video tile added, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}, isContent ${tileState.isContent}"
        )

        showVideoTile(tileState)
    }

    override fun onVideoTilePaused(tileState: VideoTileState) {
        TODO("Not yet implemented")
    }

    override fun onVideoTileRemoved(tileState: VideoTileState) {
        logger.info(
            TAG,
            "Video tile removed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}"
        )

        // Unbind the video tile to release the resource
        audioVideo.unbindVideoView(tileState.tileId)
    }

    override fun onVideoTileResumed(tileState: VideoTileState) {
        TODO("Not yet implemented")
    }

    override fun onVideoTileSizeChanged(tileState: VideoTileState) {
        TODO("Not yet implemented")
    }

    // It could be remote or local video. See the following step for how to render the tile.
    fun showVideoTile(tileState: VideoTileState) {
        audioVideo.bindVideoView(view, tileState.tileId)
    }

    override fun onMetricsReceived(metrics: Map<ObservableMetric, Any>) {
        logger.debug(TAG, "Media metrics received: $metrics")
    }

    fun loadJson(context: Context): String? {
        var input: InputStream? = null
        var jsonString: String
        try {
            input = context.assets.open("response.json")

            val size = input.available()

            val buffer = ByteArray(size)

            input.read(buffer)

            jsonString = String(buffer)
            return jsonString
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            input?.close()
        }

        return null
    }
}

private fun Nothing?.bindVideoView(view: View?, tileId: Int) {

}

private fun Nothing?.unbindVideoView(tileId: Int) {

}
