import SwiftUI

struct PersonView: View {
    var name: String = UserDefaults.standard.string(forKey: "UserName") ?? ""
    @EnvironmentObject var userSettings: UserSettings

    var body: some View {
        VStack {
            ZStack {
                Text("")
                    .frame(width: Width, height: Heigh / 4, alignment: .top)
                    .background(greenColor)
                    .cornerRadius(20, corners: [.bottomLeft, .bottomRight])

                let mark = name.prefix(1)
                Text(mark)
                    .frame(width: Heigh/8, height: Heigh/8, alignment: .center)
                    .foregroundColor(Color.white)
                    .background(lightestGreyColor)
                    .font(.system(size: Heigh/16))
                    .cornerRadius(Heigh/16, corners: .allCorners)
            }

            Text(name)
                .frame(width: Heigh/8, height: Heigh/8 - 20, alignment: .center)
                .foregroundColor(Color.white)
                .background(Color.clear)
                .font(.system(size: 25))
                .padding(EdgeInsets(top: -(Heigh/16) - 25, leading: 0, bottom: 100, trailing: 0))

            Spacer()
            Button("Logout" , action: {
                UserDefaults.standard.set(false, forKey: "loggedIn")
                self.userSettings.loggedIn = false
            })
            .frame(width: Width -  50, height: 40, alignment: .center)
            .foregroundColor(Color.red)
            .background(lightestGreyColor)
            .font(.system(size: 18, weight: .bold, design: .default))
            .cornerRadius(10)

            Spacer()
                .frame(width: Width, height: 50)
        }
        .edgesIgnoringSafeArea(.all)
    }
}

struct RoundedCorner: Shape {
    var radius: CGFloat = .infinity
    var corners: UIRectCorner = .allCorners

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: CGSize(width: radius, height: radius))
        return Path(path.cgPath)
    }
}

extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape( RoundedCorner(radius: radius, corners: corners) )
    }
}

struct PersonView_Previews: PreviewProvider {
    static var previews: some View {
        PersonView()
    }
}
