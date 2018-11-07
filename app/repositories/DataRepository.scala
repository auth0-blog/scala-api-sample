package repositories

import javax.inject.Singleton
import models.{Comment, Post}

@Singleton
class DataRepository {

  // Specify a couple of posts for our API to serve up
  private val posts = Seq(
    Post(1, "This is a blog post"),
    Post(2, "Another blog post with awesome content")
  )

  // Specify some comments for our API to serve up
  private val comments = Seq(
    Comment(1, 1, "This is an awesome blog post", "Fantastic Mr Fox"),
    Comment(2, 1, "Thanks for the insights", "Jane Doe"),
    Comment(3, 2, "Great, thanks for this post", "Joe Bloggs")
  )

  /*
   * Returns a blog post that matches the specified id, or None if no
   * post was found (collectFirst returns None if the function is undefined for the
   * given post id)
   */
  def getPost(postId: Int): Option[Post] = posts.collectFirst {
    case p if p.id == postId => p
  }

  /*
   * Returns the comments for a blog post
   * If no comments exist for the specified post id, an empty sequence is returned
   * by virtue of the fact that we're using 'collect'
   */
  def getComments(postId: Int): Seq[Comment] = comments.collect {
    case c if c.postId == postId => c
  }
}
