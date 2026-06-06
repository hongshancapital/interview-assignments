//
//  HomeView.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import SwiftUI

struct HomeView: View {
    //MARK: - Property -
    //当前用户名
    @State var username:String = CurrentUser?.username ?? ""
    //viewmodel
    let viewModel = HomeViewModel()
    //用于退出该界面
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    //MARK: - View -
    var body: some View {
        VStack {
            //顶部背景
            VStack {
                //顶部显示用户名
                Text("\(username.first?.uppercased() ?? "")")
                    .padding()
                    .frame(width: 70, height: 70, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
                    .foregroundColor(.white)
                    .background(Color.gray, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    .cornerRadius(35)
                Text("\(self.username)")
                    .foregroundColor(.white)
            }
            .frame(width:ScreenWidth ,height: 200)
            .background(RoundedCorners(color: .green, tl: 0, tr: 0, bl: 30, br: 30))
            .edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
            .statusBar(hidden: true)
            Spacer()
            //退出按钮
            Button.init(action: {
                //注销
                logoutAction()
            }, label: {
                Text("Logout")
                    .foregroundColor(.red)
            })
            .frame(width: 200, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            .background(Color.gray.opacity(0.3))
            .cornerRadius(8)
            .offset(x: 0, y: -20.0)
        }.navigationBarHidden(true)
        .statusBar(hidden: true)
    }
    //MARK: - Method -
    func logoutAction() {
        viewModel.logout { success, msg in
            if(success) {
                self.presentationMode.wrappedValue.dismiss()
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}

struct RoundedCorners: View {
    var color: Color = .blue
    var tl: CGFloat = 0.0
    var tr: CGFloat = 0.0
    var bl: CGFloat = 0.0
    var br: CGFloat = 0.0
    var body: some View {
        GeometryReader { geometry in
            Path { path in
                let w = geometry.size.width
                let h = geometry.size.height
                // Make sure we do not exceed the size of the rectangle
                let tr = min(min(self.tr, h/2), w/2)
                let tl = min(min(self.tl, h/2), w/2)
                let bl = min(min(self.bl, h/2), w/2)
                let br = min(min(self.br, h/2), w/2)

                path.move(to: CGPoint(x: w / 2.0, y: 0))
                path.addLine(to: CGPoint(x: w - tr, y: 0))
                path.addArc(center: CGPoint(x: w - tr, y: tr), radius: tr, startAngle: Angle(degrees: -90), endAngle: Angle(degrees: 0), clockwise: false)
                path.addLine(to: CGPoint(x: w, y: h - br))
                path.addArc(center: CGPoint(x: w - br, y: h - br), radius: br, startAngle: Angle(degrees: 0), endAngle: Angle(degrees: 90), clockwise: false)
                path.addLine(to: CGPoint(x: bl, y: h))
                path.addArc(center: CGPoint(x: bl, y: h - bl), radius: bl, startAngle: Angle(degrees: 90), endAngle: Angle(degrees: 180), clockwise: false)
                path.addLine(to: CGPoint(x: 0, y: tl))
                path.addArc(center: CGPoint(x: tl, y: tl), radius: tl, startAngle: Angle(degrees: 180), endAngle: Angle(degrees: 270), clockwise: false)
            }
            .fill(self.color)
        }
    }
}
