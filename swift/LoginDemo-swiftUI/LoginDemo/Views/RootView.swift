import SwiftUI

struct RootView: View {
    @State var isPresented = true
    @State var userName: String = ""
    var body: some View {
        VStack(){
            ZStack {
                RoundedRectangle(cornerRadius: 25, style: .continuous)
                    .foregroundColor(.darkGreen)
                    .shadow(radius: 10).frame(height: 260)
                VStack(spacing: 30, content: {
                    Text(userName.first?.uppercased() ?? "")
                        .font(.system(size: 30))
                        .foregroundColor(.white)
                        .frame(width: 80, height: 80)
                        .background(Color.lightGrey)
                        .mask(Rectangle()
                                .clipShape(Circle())
                        )
                    Text(userName)
                        .font(.system(size: 22))
                        .foregroundColor(.white)
                }).padding(.top, 30)
            }
        }
        .edgesIgnoringSafeArea(.all)
        Spacer()
        Button(LocalizedString.Root.logOut){
            isPresented = true
        } 
        .foregroundColor(.red)
        .frame(width: UIScreen.main.bounds.size.width - 32*2, height: .buttonHeight)
        .background(Color.lightGrey)
        .mask(Rectangle()
                .cornerRadius(10)
        )
        .fullScreenCover(isPresented: $isPresented, content: {
            LoginView(isPresented: $isPresented, userName: $userName)
        })
        Spacer().frame(height: 30)
    }
}
