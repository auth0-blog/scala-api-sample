// Make sure it's in the 'controllers' package
package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Create a simple 'ping' endpoint for now, so that we
  // can get up and running with a basic implementation
  def ping = Action { implicit request =>
    Ok("Hello, Scala!")
  }

}
