package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppTopBar
import com.example.ui.components.NavRoute
import com.example.ui.components.RewardedAdDialog
import com.example.ui.screens.admin.AdminPanelScreen
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.generators.AvatarGeneratorScreen
import com.example.ui.screens.generators.CharacterGeneratorScreen
import com.example.ui.screens.generators.CharacterMasterScreen
import com.example.ui.screens.generators.CloudBackupScreen
import com.example.ui.screens.generators.CourseCreatorScreen
import com.example.ui.screens.generators.VideoConverterExpanderScreen
import com.example.ui.screens.generators.WatermarkRemoverScreen
import com.example.ui.screens.generators.TeamWorkspaceScreen
import com.example.ui.screens.generators.CreatorAnalyticsScreen
import com.example.ui.screens.generators.GeneratorsHubScreen
import com.example.ui.screens.generators.HookGeneratorScreen
import com.example.ui.screens.generators.ImageToVideoScreen
import com.example.ui.screens.generators.ProductAdGeneratorScreen
import com.example.ui.screens.generators.ProductLinkToVideoScreen
import com.example.ui.screens.generators.MusicStudioScreen
import com.example.ui.screens.generators.PodcastGeneratorScreen
import com.example.ui.screens.generators.ProjectManagerScreen
import com.example.ui.screens.generators.PromptLibraryScreen
import com.example.ui.screens.generators.SocialAutoPostingScreen
import com.example.ui.screens.generators.ReelMakerScreen
import com.example.ui.screens.generators.ScriptWriterScreen
import com.example.ui.screens.generators.StoryGeneratorScreen
import com.example.ui.screens.generators.TalkingPhotoScreen
import com.example.ui.screens.generators.TextToImageScreen
import com.example.ui.screens.generators.ThumbnailGeneratorScreen
import com.example.ui.screens.generators.TitleHashtagGeneratorScreen
import com.example.ui.screens.generators.VideoDubbingScreen
import com.example.ui.screens.generators.VideoTemplatesScreen
import com.example.ui.screens.generators.TextToVideoScreen
import com.example.ui.screens.generators.VoiceCloneScreen
import com.example.ui.screens.generators.VoiceGeneratorScreen
import com.example.ui.screens.history.HistoryScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.referral.ReferralScreen
import com.example.ui.screens.subscription.UpgradeScreen
import com.example.ui.screens.tools.ContentCreatorToolsScreen
import com.example.ui.theme.AiVideoHubTheme
import com.example.ui.theme.DarkBackground
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AiVideoHubTheme {
                MainAppContent(viewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: MainViewModel) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()
    val history by viewModel.history.collectAsState()

    var currentRoute by remember { mutableStateOf(NavRoute.Home.route) }
    var currentSubScreen by remember { mutableStateOf<String?>(null) }
    var showRewardedAdDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                user = user,
                onWatchAdClick = {
                    if (user.rewardedAdsToday >= 5) {
                        Toast.makeText(context, "Daily rewarded ad limit reached (5/5). Try again tomorrow!", Toast.LENGTH_LONG).show()
                    } else {
                        showRewardedAdDialog = true
                    }
                },
                onUpgradeClick = { currentSubScreen = "upgrade" },
                onProfileClick = { currentRoute = NavRoute.Profile.route; currentSubScreen = null }
            )
        },
        bottomBar = {
            AppBottomNavBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                    currentSubScreen = null
                    viewModel.resetState()
                }
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DarkBackground)
        ) {
            if (currentSubScreen != null) {
                when (currentSubScreen) {
                    "text_to_video" -> TextToVideoScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "image_to_video" -> ImageToVideoScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "text_to_image" -> TextToImageScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "character_gen", "character_master", "character_consistency_pro" -> CharacterMasterScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "voice_gen" -> VoiceGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "voice_clone" -> VoiceCloneScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "talking_photo" -> TalkingPhotoScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "thumbnail_gen" -> ThumbnailGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "video_dubbing" -> VideoDubbingScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "avatar_gen" -> AvatarGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "story_gen" -> StoryGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "video_templates" -> VideoTemplatesScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "reel_maker" -> ReelMakerScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "podcast_gen" -> PodcastGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "script_writer" -> ScriptWriterScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "hook_gen" -> HookGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "title_hashtag" -> TitleHashtagGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "prompt_library" -> PromptLibraryScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "creator_analytics" -> CreatorAnalyticsScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "project_manager" -> ProjectManagerScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "social_auto_post" -> SocialAutoPostingScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "cloud_backup" -> CloudBackupScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "team_workspace" -> TeamWorkspaceScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "course_creator" -> CourseCreatorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "long_to_short" -> VideoConverterExpanderScreen(viewModel = viewModel, initialMode = "long_to_short", onBackClick = { currentSubScreen = null })
                    "short_to_long" -> VideoConverterExpanderScreen(viewModel = viewModel, initialMode = "short_to_long", onBackClick = { currentSubScreen = null })
                    "watermark_remover" -> WatermarkRemoverScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "music_gen", "music_studio" -> MusicStudioScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "product_to_video", "product_ad_gen" -> ProductAdGeneratorScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "referral" -> ReferralScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "upgrade" -> UpgradeScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "admin" -> AdminPanelScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                    "auth" -> AuthScreen(onLoginSuccess = { name, email, loginType ->
                        viewModel.login(name, email, loginType)
                        currentSubScreen = null
                        Toast.makeText(context, "Welcome back, $name! 👋", Toast.LENGTH_SHORT).show()
                    })
                    else -> TextToVideoScreen(viewModel = viewModel, onBackClick = { currentSubScreen = null })
                }
            } else {
                when (currentRoute) {
                    NavRoute.Home.route -> HomeScreen(
                        user = user,
                        recentGenerations = history,
                        onWatchAdClick = { showRewardedAdDialog = true },
                        onUpgradeClick = { currentSubScreen = "upgrade" },
                        onToolClick = { toolId -> currentSubScreen = toolId }
                    )

                    NavRoute.Generators.route -> GeneratorsHubScreen(
                        onSelectGenerator = { toolId -> currentSubScreen = toolId }
                    )

                    NavRoute.ContentTools.route -> ContentCreatorToolsScreen(
                        viewModel = viewModel
                    )

                    NavRoute.History.route -> HistoryScreen(
                        viewModel = viewModel
                    )

                    NavRoute.Profile.route -> ProfileScreen(
                        viewModel = viewModel,
                        onWatchAdClick = { showRewardedAdDialog = true },
                        onUpgradeClick = { currentSubScreen = "upgrade" },
                        onAdminClick = { currentSubScreen = "admin" },
                        onReferralClick = { currentSubScreen = "referral" }
                    )
                }
            }

            // Rewarded Ad Dialog
            if (showRewardedAdDialog) {
                RewardedAdDialog(
                    onDismiss = { showRewardedAdDialog = false },
                    onRewardEarned = {
                        viewModel.watchRewardedAd { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
        }
    }
}

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AiScriptMakerScreen(onBackClick: () -> Unit) {
    var topic by remember { mutableStateOf("") }
    var selectedPlatform by remember { mutableStateOf("Insta Reel") }
    var showResult by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "✨ AI Script & Idea Maker",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Topic Box
        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Enter your topic (e.g., Weight loss tips)", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF00E676),
                unfocusedIndicatorColor = Color.DarkGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Platform Selection
        Text("Select Platform:", color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Insta Reel", "YouTube", "Shorts").forEach { platform ->
                Button(
                    onClick = { selectedPlatform = platform },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPlatform == platform) Color(0xFF00E676) else Color.DarkGray
                    )
                ) {
                    Text(platform, color = if (selectedPlatform == platform) Color.Black else Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Magic Button
        Button(
            onClick = { showResult = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Generate Magic 🪄", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result Box (Dummy result for UI testing)
        if (showResult && topic.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🪝 Hook: Are you making this simple mistake?", color = Color(0xFF00E676), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("📜 Script: Welcome back! Today we will talk about $topic. Here are 3 simple tricks to master it...", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("🏷️ Tags: #$topic #trending #viral", color = Color.Cyan)
                }
            }
        }
    }
}
