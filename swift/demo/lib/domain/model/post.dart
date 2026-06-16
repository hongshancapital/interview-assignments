// 条目实体类
class Post {
  // 条目图片
  final String iconUrl;
  // 条目名称
  final String trackCensoredName;
  // 条目简介
  final String description;
  // 是否收藏
  final bool isFavorite;

  Post(this.iconUrl, this.trackCensoredName, this.description, {this.isFavorite = false});

  Post copy(bool isFavorite) => Post(iconUrl, trackCensoredName, description, isFavorite: isFavorite);

  factory Post.tempPost() {
    return Post("https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/bf/85/91/bf85919e-e88e-aa4c-4488-fac6c8815850/source/100x100bb.jpg",
    "ZOOM Cloud Meetings",
    "Zoom is #1 in customer satisfaction and the best unified communication experience on mobile.\n\nIt's super easy! Install the free Zoom app, click on \"New Meeting,\" and invite up to 100 people to join you on video! Connect with anyone on iPad, iPhone, other mobile devices, Windows, Mac, Zoom Rooms, H.323/SIP room systems, and telephones.\n\nVIDEO MEETINGS FROM ANYWHERE\n-Best video meeting quality\n-Easily join a meeting or start an instant meeting with phone, email",isFavorite: false);
  }

  @override
  String toString() {
    return "Post(\"$iconUrl\", \"$trackCensoredName\", \"$description\", isFavorite: $isFavorite)";
  }

  factory Post.fromJson(dynamic data) {
    var des = data["description"] as String;
    if (des.length > 100) {
      des = des.substring(0,100);
    }
    return Post(data["artworkUrl100"], data["trackCensoredName"], des, isFavorite: false);
  }

  @override
  bool operator ==(Object other) {
    if (other is Post) {
      return other.iconUrl == iconUrl;
    }
    return false;
  }

  @override
  int get hashCode => iconUrl.hashCode;
}

extension CreatorEntry on Post {
}