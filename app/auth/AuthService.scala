package auth

import com.auth0.jwk.UrlJwkProvider
import javax.inject.Inject
import pdi.jwt.{JwtAlgorithm, JwtBase64, JwtClaim, JwtJson}
import play.api.Configuration

import scala.util.{Failure, Success, Try}

class AuthService @Inject()(config: Configuration) {

  // A regex that defines the JWT pattern and allows us to
  // extract the header, claims and signature
  private val jwtRegex = """(.+?)\.(.+?)\.(.+?)""".r

  // Your Auth0 domain, read from configuration
  private def domain = config.get[String]("auth0.domain")
  private def audience = config.get[String]("auth0.audience")

  // Validates a JWT and potentially returns the claims if the token was successfully parsed
  def validateJwt(token: String): Try[JwtClaim] = for {
    jwk <- getJwk(token)           // Get the secret key for this token
    claims <- JwtJson.decode(token, jwk.getPublicKey, Seq(JwtAlgorithm.RS256)) // Decode the token using the secret key
    _ <- validateClaims(claims)     // validate the issuer and the audience
  } yield claims

  // Splits a JWT into it's 3 component parts
  private val splitToken = (jwt: String) => jwt match {
    case jwtRegex(header, body, sig) => Success((header, body, sig))
    case _ => Failure(new Exception("Token does not match the correct pattern"))
  }

  // As the header and claims data are base64-encoded, this function
  // decodes those elements
  private val decodeElements = (data: Try[(String, String, String)]) => data map {
    case (header, body, sig) => (JwtBase64.decodeString(header), JwtBase64.decodeString(body), sig)
  }

  // Gets the JWK from the JWKS endpoint using the jwks-rsa library
  private val getJwk = (token: String) =>
    (splitToken andThen decodeElements) (token) flatMap {
      case (header, _, _) =>
        val jwtHeader = JwtJson.parseHeader(header)     // extract the header
        val jwkProvider = new UrlJwkProvider(s"https://$domain")

        // Use jwkProvider to load the JWKS data and return the JWK
        jwtHeader.keyId.map(k => Try(jwkProvider.get(k))) getOrElse Failure(new Exception("Unable to retrieve kid"))
    }

  private val validateClaims = (claims: JwtClaim) => {
    val issuer = s"https://$domain/"

    (claims.audience, claims.issuer) match {
      case (Some(s), Some(`issuer`))
        if s.contains(audience) => Success(claims)
      case _ => Failure(new Exception("Token has not passed validation"))
    }
  }
}