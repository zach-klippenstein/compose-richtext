package com.zachklipp.richtext.sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.ui.tooling.preview.Preview
import com.zachklipp.richtext.ui.FormattedList2
import com.zachklipp.richtext.ui.ListType.Ordered
import com.zachklipp.richtext.ui.RichText
import com.zachklipp.richtext.ui.slideshow.BodySlide
import com.zachklipp.richtext.ui.slideshow.NavigableContentContainer
import com.zachklipp.richtext.ui.slideshow.NavigableContentScope
import com.zachklipp.richtext.ui.slideshow.SlideDivider
import com.zachklipp.richtext.ui.slideshow.SlideNumberFooter
import com.zachklipp.richtext.ui.slideshow.SlideScope
import com.zachklipp.richtext.ui.slideshow.Slideshow
import com.zachklipp.richtext.ui.slideshow.TitleSlide

@Preview(showBackground = true)
@Composable fun SlideshowSample() {
  Slideshow(
    {
      TitleSlide(
        title = { Text("Title") },
        subtitle = { Text("Subtitle") }
      )
    },
    {
      BodySlide(header = { Text("Header without Divider") }, body = {
        Text(
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Purus viverra accumsan in nisl nisi. Amet venenatis urna cursus eget nunc scelerisque viverra. Ultrices dui sapien eget mi. Ornare quam viverra orci sagittis eu volutpat. Quam viverra orci sagittis eu volutpat odio facilisis. Turpis nunc eget lorem dolor. Nunc pulvinar sapien et ligula ullamcorper malesuada proin libero. Molestie at elementum eu facilisis sed odio morbi quis commodo. Nec feugiat nisl pretium fusce id velit ut. Tortor posuere ac ut consequat semper viverra nam. Sit amet est placerat in egestas erat. Accumsan sit amet nulla facilisi morbi. Senectus et netus et malesuada fames ac turpis egestas. Lacus sed viverra tellus in hac. Aliquet porttitor lacus luctus accumsan tortor posuere ac ut. Pellentesque nec nam aliquam sem et tortor consequat id. Tellus cras adipiscing enim eu. Feugiat in fermentum posuere urna nec tincidunt praesent.",
          fontFamily = FontFamily.Serif
        )
      })
    },
    {
      BodySlide(
        header = {
          Text("Header with Divider")
          SlideDivider()
        },
        body = {
          Text("Content 1")
          Text("Content 2")
        },
        footer = { SlideNumberFooter() }
      )
    },
    {
      BodySlide(
        header = {
          Text("Animated Paragraphs")
          SlideDivider()
        },
        body = { AnimatedParagraphsSlide() },
        footer = { SlideNumberFooter() }
      )
    },
    {
      BodySlide(
        header = {
          Text("Animated ordered list items")
          SlideDivider()
        },
        body = { AnimatedOrderedListSlide() },
        footer = { SlideNumberFooter() }
      )
    },
    {
      Text("Simple content without a scaffold.")
    },
  )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable private fun SlideScope.AnimatedOrderedListSlide() {
  @Composable fun NavigableContentScope.SlideRow(children: @Composable () -> Unit) {
    NavigableContent { visible ->
      AnimatedVisibility(
          visible.value,
          enter = slideInHorizontally({ it * 2 }),
          exit = slideOutHorizontally({ it * 2 }),
      ) {
        children()
      }
    }
  }

  NavigableContentContainer {
    RichText {
      FormattedList2(
          listType = Ordered,
//        items = listOf(
//          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
//          "Etiam dignissim diam quis enim lobortis scelerisque fermentum.",
//          "Duis convallis convallis tellus id interdum velit.",
//          "Congue eu consequat ac felis donec et odio pellentesque.",
//        )
      ) {
//        NavigableContent { visible ->
//          AnimatedVisibility(
//            visible.value,
//            enter = expandIn(),
//            exit = shrinkOut(),
//          ) {
//            Text(it)
//          }
//        }
        SlideRow {
          ListItem(Modifier) {
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua.",
            )
          }
        }
        SlideRow {
          ListItem(Modifier) {
            Text(
                "Etiam dignissim diam quis enim lobortis scelerisque fermentum."
            )
          }
        }
        SlideRow {
          ListItem(Modifier) {
            Text(
                "Duis convallis convallis tellus id interdum velit."
            )
          }
        }
        SlideRow {
          ListItem(Modifier) {
            Text(
                "Congue eu consequat ac felis donec et odio pellentesque."
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable private fun SlideScope.AnimatedParagraphsSlide() {
  NavigableContentContainer {
    Column {
      // This paragraph is always visible.
      Text("◦ One paragraph. This is some stuff you should read.")
      NavigableContent { visible ->
        AnimatedVisibility(
          visible.value,
          enter = expandIn(),
          exit = shrinkOut(),
        ) {
          Text("◦ Second paragraph. This is some more stuff you should read.")
        }
      }
      NavigableContent { visible ->
        AnimatedVisibility(
          visible.value,
          enter = fadeIn(),
          exit = fadeOut(),
        ) {
          Text(annotatedString {
            append("◦ 3rd paragraph.\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
              append("  This is the most important stuff!")
            }
          })
        }
      }
      Spacer(Modifier.weight(1f, fill = true))
      NavigableContent { visible ->
        AnimatedVisibility(
          visible.value,
          modifier = Modifier.fillMaxWidth(),
          enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
          exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
        ) {
          Text("Fourth paragraph! No bullet.")
        }
      }
    }
  }
}
