import SwiftUI

struct RootView: View {
    @Binding var isRoot: Bool
    var body: some View {
        VStack {
            ZStack(alignment: .center) {
                RoundedCorners(color: Color.green, topL: 0, topR: 0, bottomL: 30, bottomR: 30)
                    .frame(height: 300)
                VStack(alignment:.center) {
                    ZStack {
                        Rectangle()
                            .fill(Color(hex: 0xf7f7f7))
                            .frame(width: 100, height: 100)
                            .cornerRadius(50)
                        
                        if UserDefaultsConfig.username.count > 0 {
                            let initialsString = UserDefaultsConfig.username.dropFirst(0).prefix(1)
                            Text(initialsString)
                                .bold()
                                .font(.system(size: 40))
                                .foregroundColor(.white)
                        }
                    }
                    
                    Text(UserDefaultsConfig.username)
                        .bold()
                        .font(.system(size: 30))
                        .foregroundColor(.white)
                }.padding(.top,40)
            }
            Spacer()
            Button(action: {
                isRoot = false
                UserDefaultsConfig.username = ""
            }, label: {
                HStack {
                    Text("Login out")
                        .bold()

                    Image(systemName: "arrow.left")
                        .font(.system(size: 14))
                }
            })
            .frame(width: kScreenWidth - 30*2, height: 44, alignment: .center)
            .background(Color.red )
            .foregroundColor(.white)
            .cornerRadius(15)
            Spacer()
        }.edgesIgnoringSafeArea(.all)
        
    }
}

struct RoundedCorners: View {
    var color: Color = .blue
    var topL: CGFloat = 0.0
    var topR: CGFloat = 0.0
    var bottomL: CGFloat = 0.0
    var bottomR: CGFloat = 0.0

    var body: some View {
        GeometryReader { geometry in
            Path { path in
                let width = geometry.size.width
                let height = geometry.size.height

                // Make sure we do not exceed the size of the rectangle
                let topRight = min(min(self.topR, height/2), width/2)
                let topLeft = min(min(self.topL, height/2), width/2)
                let bottomLeft = min(min(self.bottomL, height/2), width/2)
                let bottomRight = min(min(self.bottomR, height/2), width/2)

                path.move(to: CGPoint(x: width / 2.0, y: 0))
                path.addLine(to: CGPoint(x: width - topRight, y: 0))
                path.addArc(center: CGPoint(x: width - topRight, y: topRight), radius: topRight, startAngle: Angle(degrees: -90), endAngle: Angle(degrees: 0), clockwise: false)
                path.addLine(to: CGPoint(x: width, y: height - bottomRight))
                path.addArc(center: CGPoint(x: width - bottomRight, y: height - bottomRight), radius: bottomRight, startAngle: Angle(degrees: 0), endAngle: Angle(degrees: 90), clockwise: false)
                path.addLine(to: CGPoint(x: bottomLeft, y: height))
                path.addArc(center: CGPoint(x: bottomLeft, y: height - bottomLeft), radius: bottomLeft, startAngle: Angle(degrees: 90), endAngle: Angle(degrees: 180), clockwise: false)
                path.addLine(to: CGPoint(x: 0, y: topLeft))
                path.addArc(center: CGPoint(x: topLeft, y: topLeft), radius: topLeft, startAngle: Angle(degrees: 180), endAngle: Angle(degrees: 270), clockwise: false)
            }
            .fill(self.color)
        }
    }
}
