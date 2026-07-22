//
//  TodoListProjectApp.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/11.
//

import SwiftUI

@main
struct TodoListProjectApp: App {
    
    init() {
        setupApperance()
    }
    
    var body: some Scene {
        WindowGroup {
            TodoListHome()
        }
    }
    
    private func setupApperance() {
        
        UINavigationBar.appearance().largeTitleTextAttributes = [
            NSAttributedString.Key.foregroundColor: UIColor.darkGray]
        
        UINavigationBar.appearance().titleTextAttributes = [
            NSAttributedString.Key.foregroundColor: UIColor.darkGray]
        
        UIBarButtonItem.appearance().setTitleTextAttributes([
            NSAttributedString.Key.foregroundColor: UIColor.darkGray,
        ], for: .normal)
        
        UIWindow.appearance().tintColor = UIColor.darkGray
        
        UITableView.appearance().separatorColor = .clear
    }
}

extension UIApplication{
    func endEditing(){
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
