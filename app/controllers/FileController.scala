package controllers

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.amazonaws.util.IOUtils
import javax.inject._
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class FileController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
    def resume(b: Option[String]): Action[AnyContent] = Action {
        def downloadResume(branch: String): Array[Byte] = {
            val client: AmazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build()

            val resumeObject: S3Object = client.getObject("andrewsmithresume", s"resume_b$branch.pdf")
            IOUtils.toByteArray(resumeObject.getObjectContent)
        }

        val resumeBytes: Array[Byte] = downloadResume(b.getOrElse("0"))

        Ok(resumeBytes).as("application/pdf")
    }
}
