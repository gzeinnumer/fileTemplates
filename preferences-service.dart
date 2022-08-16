import 'package:shared_preferences/shared_preferences.dart';

class PreferencesService {
  static const String KEY_USERNAME = "USERNAME";

  Future setSession(String key, String data) async {
    final preferences = await SharedPreferences.getInstance();
    await preferences.setString(key, data);
  }

  Future<String> getSession(String key) async {
    final preferences = await SharedPreferences.getInstance();
    final value = preferences.getString(key) ?? "";
    return value;
  }
}